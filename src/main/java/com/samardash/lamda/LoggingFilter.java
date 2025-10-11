package com.samardash.lamda;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;

@Slf4j
@Component
public class LoggingFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        setRequestId();

        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);

        // Log request details
        logRequest(requestWrapper);

        try {
            filterChain.doFilter(requestWrapper, responseWrapper);
        } finally {
            // Log response details
            logResponse(responseWrapper);
            responseWrapper.copyBodyToResponse();
        }
    }

    private void logRequest(ContentCachingRequestWrapper request) {
        StringBuilder sb = new StringBuilder();
        sb.append(">>> ").append(request.getMethod()).append(" ").append(getFullPath(request));
        
        // Add headers
        Collections.list(request.getHeaderNames()).forEach(header ->
                sb.append(" | ").append(header).append(": ").append(request.getHeader(header)));
        
        // Add body
        byte[] content = request.getContentAsByteArray();
        if (content.length > 0) {
            sb.append(" | Body: ").append(new String(content, StandardCharsets.UTF_8));
        }
        
        log.info(sb.toString());
    }

    private void logResponse(ContentCachingResponseWrapper response) {
        StringBuilder sb = new StringBuilder();
        sb.append("<<< HTTP/1.1 ").append(response.getStatus());
        
        // Add headers
        response.getHeaderNames().forEach(header ->
                sb.append(" | ").append(header).append(": ").append(response.getHeader(header)));
        
        // Add body
        byte[] content = response.getContentAsByteArray();
        if (content.length > 0) {
            sb.append(" | Body: ").append(new String(content, StandardCharsets.UTF_8));
        }
        
        log.info(sb.toString());
    }

    private String getFullPath(HttpServletRequest request) {
        String path = request.getRequestURI();
        String query = request.getQueryString();
        return query != null ? path + "?" + query : path;
    }

    private static void setRequestId() {
        String existingRequestId = MDC.get("Request-ID");
        if (existingRequestId == null || existingRequestId.isEmpty()) {
            String requestId = java.util.UUID.randomUUID().toString();
            MDC.put("Request-ID", requestId);
        }
    }
}