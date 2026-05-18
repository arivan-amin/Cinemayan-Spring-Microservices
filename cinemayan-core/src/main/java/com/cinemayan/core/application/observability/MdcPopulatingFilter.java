package com.cinemayan.core.application.observability;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
@Order (Ordered.HIGHEST_PRECEDENCE)
class MdcPopulatingFilter extends OncePerRequestFilter {

    private static final String CORRELATION_ID_HEADER = "X-Correlation-ID";
    private static final String CORRELATION_ID_MDC_KEY = "correlation_id";
    private static final String LOG_TYPE_MDC_KEY = "log_type";
    private static final String DEFAULT_LOG_TYPE = "application";

    @Override
    protected void doFilterInternal (HttpServletRequest request, HttpServletResponse response,
                                     FilterChain filterChain) throws ServletException, IOException {
        try {
            enrichMdc(request, response);
            filterChain.doFilter(request, response);
        }
        finally {
            clearMdc();
        }
    }

    private void enrichMdc (HttpServletRequest request, HttpServletResponse response) {
        String correlationId = resolveCorrelationId(request);
        MDC.put(CORRELATION_ID_MDC_KEY, correlationId);
        MDC.put(LOG_TYPE_MDC_KEY, DEFAULT_LOG_TYPE);
        response.setHeader(CORRELATION_ID_HEADER, correlationId);
    }

    private void clearMdc () {
        MDC.remove(CORRELATION_ID_MDC_KEY);
        MDC.remove(LOG_TYPE_MDC_KEY);
    }

    private String resolveCorrelationId (HttpServletRequest request) {
        String fromHeader = request.getHeader(CORRELATION_ID_HEADER);
        return StringUtils.hasText(fromHeader) ? fromHeader : UUID.randomUUID()
            .toString();
    }
}
