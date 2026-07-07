package com.likelion.miniproject.global.security.jwt;

import com.likelion.miniproject.global.security.exception.AuthErrorCode;
import com.likelion.miniproject.global.security.exception.AuthException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * JWT 발급/파싱/검증을 전부 담당하는 단일 클래스.
 * (원본 hexagonal 버전의 JwtTokenProvider + JwtTokenAdapter + JwtAuthenticationConverter를 합침)
 */
@Component
public class JwtTokenProvider {

    private final JwtProperties jwtProperties;
    private final SecretKey secretKey;

    public JwtTokenProvider(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
        this.secretKey = Keys.hmacShaKeyFor(
                jwtProperties.getSecret().getBytes(StandardCharsets.UTF_8)
        );
    }

    public String createAccessToken(Long userId, String username, String role) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + jwtProperties.getAccessTokenExpiration());

        return Jwts.builder()
                .subject(String.valueOf(userId))
                .claim("username", username)
                .claim("role", role)
                .issuedAt(now)
                .expiration(expiration)
                .signWith(secretKey, Jwts.SIG.HS256)
                .compact();
    }

    public String createRefreshToken(Long userId, String username) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + jwtProperties.getRefreshTokenExpiration());

        return Jwts.builder()
                .subject(String.valueOf(userId))
                .claim("username", username)
                .issuedAt(now)
                .expiration(expiration)
                .signWith(secretKey, Jwts.SIG.HS256)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            parseClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public boolean isTokenExpired(String token) {
        try {
            parseClaims(token);
            return false;
        } catch (ExpiredJwtException e) {
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    /** 만료/서명 검증 + claim 파싱까지 한 번에 하고, 바로 SecurityContext에 넣을 AuthUser를 반환한다. */
    public AuthUser authenticate(String accessToken) {
        if (accessToken == null || accessToken.isBlank()) {
            throw new AuthException(AuthErrorCode.ACCESS_TOKEN_MISSING);
        }
        if (isTokenExpired(accessToken)) {
            throw new AuthException(AuthErrorCode.ACCESS_TOKEN_EXPIRED);
        }
        if (!validateToken(accessToken)) {
            throw new AuthException(AuthErrorCode.INVALID_ACCESS_TOKEN);
        }

        Claims claims = parseClaims(accessToken);
        Long userId = parseSubjectAsUserId(claims);
        String username = claims.get("username", String.class);
        String role = claims.get("role", String.class);

        if (username == null || username.isBlank()) {
            throw new AuthException(AuthErrorCode.USERNAME_CLAIM_MISSING);
        }
        if (role == null || role.isBlank()) {
            throw new AuthException(AuthErrorCode.ROLE_CLAIM_MISSING);
        }

        return new AuthUser(userId, username, role);
    }

    public RefreshTokenInfo parseRefreshToken(String token) {
        Claims claims = parseClaims(token);

        Long userId = parseSubjectAsUserId(claims);
        String username = claims.get("username", String.class);

        if (username == null || username.isBlank()) {
            throw new AuthException(AuthErrorCode.USERNAME_CLAIM_MISSING);
        }

        return new RefreshTokenInfo(userId, username);
    }

    private Claims parseClaims(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private Long parseSubjectAsUserId(Claims claims) {
        String subject = claims.getSubject();
        if (subject == null || subject.isBlank()) {
            throw new AuthException(AuthErrorCode.INVALID_TOKEN_SUBJECT);
        }
        try {
            return Long.valueOf(subject);
        } catch (NumberFormatException e) {
            throw new AuthException(AuthErrorCode.INVALID_TOKEN_SUBJECT);
        }
    }
}
