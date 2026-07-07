package com.likelion.miniproject.global.security.jwt;

import com.likelion.miniproject.global.security.exception.AuthException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

/**
 * 요청마다 Authorization 헤더(Bearer) 또는 accessToken 쿠키에서 토큰을 꺼내
 * JwtTokenProvider로 검증하고 SecurityContext에 인증 정보를 채워 넣는다.
 * (원본 hexagonal 버전의 JwtAuthenticationFilter + AccessTokenAuthenticationService +
 *  AuthenticateAccessTokenUseCase + JwtAuthenticationConverter를 이 클래스 하나로 합침)
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String BEARER_PREFIX = "Bearer ";

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String token = resolveToken(request);

        if (StringUtils.hasText(token)) {
            try {
                AuthUser authUser = jwtTokenProvider.authenticate(token);

                var authentication = new UsernamePasswordAuthenticationToken(
                        authUser,
                        null,
                        List.of(new SimpleGrantedAuthority(authUser.role()))
                );
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (AuthException e) {
                request.setAttribute("authErrorCode", e.getErrorCode());
            }
        }

        filterChain.doFilter(request, response);
    }

    private String resolveToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (StringUtils.hasText(authorizationHeader)
                && authorizationHeader.startsWith(BEARER_PREFIX)) {

            String token = authorizationHeader.substring(BEARER_PREFIX.length());

            if (StringUtils.hasText(token)
                    && !"null".equals(token)
                    && !"undefined".equals(token)) {
                return token;
            }
        }

        if (request.getCookies() == null) {
            return null;
        }

        for (Cookie cookie : request.getCookies()) {
            if ("accessToken".equals(cookie.getName())
                    && StringUtils.hasText(cookie.getValue())) {
                return cookie.getValue();
            }
        }

        return null;
    }
}
