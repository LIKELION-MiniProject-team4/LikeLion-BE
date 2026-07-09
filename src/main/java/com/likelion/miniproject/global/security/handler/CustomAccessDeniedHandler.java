package com.likelion.miniproject.global.security.handler;

import com.likelion.miniproject.global.response.GlobalApiErrorResponse;
import com.likelion.miniproject.global.security.exception.AuthErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

// 인가 실패(403) 응답
@Slf4j
@Component
@RequiredArgsConstructor
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper;

    @Override
    public void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            AccessDeniedException accessDeniedException
    ) throws IOException {

        AuthErrorCode errorCode = AuthErrorCode.ACCESS_DENIED;
        String traceId = MDC.get("traceId");

        log.warn(
                "event=access_denied status={} method={} uri={} remoteAddr={} userAgent={} traceId={} message={}",
                errorCode.getHttpStatus().value(),
                request.getMethod(),
                request.getRequestURI(),
                request.getRemoteAddr(),
                request.getHeader("User-Agent"),
                traceId,
                accessDeniedException.getMessage()
        );

        response.setStatus(errorCode.getHttpStatus().value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");

        objectMapper.writeValue(
                response.getWriter(),
                GlobalApiErrorResponse.of(errorCode, traceId)
        );
    }
}
