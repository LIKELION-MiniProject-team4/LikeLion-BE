package com.likelion.miniproject.user.token;

import com.likelion.miniproject.global.security.jwt.JwtProperties;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;

/**
 * 응답 포맷은 프로젝트마다 컨벤션이 다르므로 래퍼 없이 DTO를 그대로 반환한다.
 * 프로젝트에 공통 응답 포맷(GlobalApiResponse 등)이 있으면 그쪽 컨벤션에 맞게 감싸서 쓸 것.
 */
@Tag(name = "Token", description = "accessToken 검증 / 재발급 API")
@RestController
@RequestMapping("/api/token")
@RequiredArgsConstructor
public class TokenController {

    private final TokenService tokenService;
    private final JwtProperties jwtProperties;

    @Operation(summary = "accessToken 유효성 검증", description = "Authorization 헤더의 accessToken이 유효하면 200을 반환한다.")
    @GetMapping("/validate")
    public ResponseEntity<Void> validateAccessToken() {
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "accessToken 재발급", description = "쿠키의 refreshToken을 검증해 새 accessToken(바디)과 새 refreshToken(쿠키)을 발급한다. rotation 방식이라 기존 refreshToken은 즉시 무효화된다.")
    @PostMapping("/reissue")
    public ResponseEntity<ReissueTokenResponse> reissueAccessToken(
            @CookieValue(name = "refreshToken", required = false) String refreshToken
    ) {
        TokenService.ReissueResult result = tokenService.reissueAccessToken(refreshToken);

        ResponseCookie refreshTokenCookie = ResponseCookie.from("refreshToken", result.refreshToken())
                .httpOnly(true)
                .secure(true)
                .sameSite("Lax")
                .path("/")
                .maxAge(Duration.ofMillis(jwtProperties.getRefreshTokenExpiration()))
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString())
                .body(new ReissueTokenResponse(result.accessToken()));
    }
}
