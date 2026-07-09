package com.likelion.miniproject.global.security.handler;

import com.likelion.miniproject.global.response.GlobalApiErrorResponse;
import com.likelion.miniproject.global.security.exception.AuthErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.MDC;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

// 인증 실패(401) 응답
@Component
@RequiredArgsConstructor
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException
    ) throws IOException, ServletException {

        AuthErrorCode errorCode = resolveAuthErrorCode(request);

        response.setStatus(errorCode.getHttpStatus().value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");

        objectMapper.writeValue(
                response.getWriter(),
                GlobalApiErrorResponse.of(errorCode, MDC.get("traceId"))
        );
    }

    private AuthErrorCode resolveAuthErrorCode(HttpServletRequest request) {
        Object attribute = request.getAttribute("authErrorCode");
        if (attribute instanceof AuthErrorCode authErrorCode) {
            return authErrorCode;
        }
        return AuthErrorCode.ACCESS_TOKEN_MISSING;
    }
}
