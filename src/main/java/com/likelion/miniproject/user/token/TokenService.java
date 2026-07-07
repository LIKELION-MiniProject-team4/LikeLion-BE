package com.likelion.miniproject.user.token;

import com.likelion.miniproject.global.security.exception.AuthErrorCode;
import com.likelion.miniproject.global.security.exception.AuthException;
import com.likelion.miniproject.global.security.jwt.JwtTokenProvider;
import com.likelion.miniproject.global.security.jwt.RefreshTokenInfo;
import com.likelion.miniproject.user.AuthUserInfo;
import com.likelion.miniproject.user.AuthUserLookup;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 컨트롤러 -> 서비스 -> 리포지토리로 바로 이어지는 MVC 스타일.
 * 포트/유스케이스 인터페이스 없이, 이 클래스가 검증 + rotation 로직을 전부 갖고 있다.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class TokenService {

    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final AuthUserLookup authUserLookup;

    public record ReissueResult(String accessToken, String refreshToken) {
    }

    @Transactional
    public ReissueResult reissueAccessToken(String refreshToken) {
        log.info("event=accessToken_reissue_start");

        if (refreshToken == null || refreshToken.isBlank()) {
            throw new AuthException(AuthErrorCode.REFRESH_TOKEN_NOT_FOUND);
        }

        // 1. refreshToken 자체가 유효한 JWT인지 검증
        if (!jwtTokenProvider.validateToken(refreshToken)) {
            throw new AuthException(AuthErrorCode.INVALID_REFRESH_TOKEN);
        }

        // 2. refreshToken에서 userId 추출
        RefreshTokenInfo tokenInfo = jwtTokenProvider.parseRefreshToken(refreshToken);
        Long userId = tokenInfo.userId();

        // 3. DB에 저장된 refreshToken 조회
        RefreshToken savedRefreshToken = refreshTokenRepository.findByUserId(userId)
                .orElseThrow(() -> new AuthException(AuthErrorCode.REFRESH_TOKEN_NOT_FOUND));

        // 4. 요청으로 들어온 refreshToken과 DB refreshToken 비교
        //    불일치 시 = 이미 rotation으로 폐기된 토큰의 재사용 시도(탈취 의심 신호)
        if (!savedRefreshToken.getToken().equals(refreshToken)) {
            log.warn("event=refreshToken_reuse_detected userId={}", userId);
            throw new AuthException(AuthErrorCode.REFRESH_TOKEN_MISMATCH);
        }

        // 5. 사용자 조회
        AuthUserInfo user = authUserLookup.findById(userId)
                .orElseThrow(() -> new AuthException(AuthErrorCode.USER_NOT_FOUND));

        // 6. 새 accessToken + 새 refreshToken 발급 (rotation)
        String newAccessToken = jwtTokenProvider.createAccessToken(user.id(), user.username(), user.role());
        String newRefreshToken = jwtTokenProvider.createRefreshToken(user.id(), user.username());

        // 7. DB의 refreshToken을 새 값으로 교체 -> 방금 쓴 옛 토큰은 이 시점부터 무효
        savedRefreshToken.rotate(newRefreshToken);

        log.info("event=accessToken_reissue_succeed userId={}, role={}", user.id(), user.role());

        return new ReissueResult(newAccessToken, newRefreshToken);
    }
}
