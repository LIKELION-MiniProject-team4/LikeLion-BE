package com.likelion.miniproject.global.logging;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

/**
 * 요청마다 traceId를 발급해서 MDC에 심어두는 필터.
 * Spring Security 필터 체인보다 먼저 실행돼야(인증 실패로 튕기는 요청도 로그에 traceId가 남게)
 * @Order(HIGHEST_PRECEDENCE)로 필터 체인 맨 앞에 등록한다.
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class TraceIdFilter extends OncePerRequestFilter {

    private static final String TRACE_ID_KEY = "traceId";
    private static final String TRACE_ID_HEADER = "X-Trace-Id";

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        String traceId = UUID.randomUUID().toString().substring(0, 8);
        try {
            MDC.put(TRACE_ID_KEY, traceId);
            response.setHeader(TRACE_ID_HEADER, traceId);
            filterChain.doFilter(request, response);
        } finally {
            // 톰캣이 스레드를 재사용하므로, 다음 요청에 이전 traceId가 새는 걸 막기 위해 반드시 제거한다.
            MDC.remove(TRACE_ID_KEY);
        }
    }
}
