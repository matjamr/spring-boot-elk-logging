package com.beaverly.springbootelklogging.mdc;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.util.UUID;

@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Slf4jMDCFilter extends OncePerRequestFilter {
    public static final String CORRELATION_ID = "CorrelationId";

    @Override
    protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response, final FilterChain chain)
            throws java.io.IOException, ServletException {
        try {
            final String correlationId;
            if (!StringUtils.isEmpty(request.getHeader(CORRELATION_ID))) {
                correlationId = request.getHeader(CORRELATION_ID);
            } else {
                correlationId = UUID.randomUUID().toString().toUpperCase().replace("-", "");
            }

            MDC.put(CORRELATION_ID, correlationId);

            if (!StringUtils.isEmpty(CORRELATION_ID)) {
                response.addHeader(CORRELATION_ID, correlationId);
            }
            chain.doFilter(request, response);
        } finally {
            MDC.remove(CORRELATION_ID);
        }
    }
}
