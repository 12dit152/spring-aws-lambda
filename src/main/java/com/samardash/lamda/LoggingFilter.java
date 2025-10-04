package com.samardash.lamda;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
public class LoggingFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        setRequestId();
        // Log the user agent
        log.info("User-Agent: {}", request.getHeader("User-Agent"));
        // Add request ID to response header
        response.setHeader("Request-ID", MDC.get("Request-ID"));
        // Proceed with the next filter in the chain
        filterChain.doFilter(request, response);
    }

    private static void setRequestId() {
        String existingRequestId = MDC.get("Request-ID");
        if (existingRequestId == null || existingRequestId.isEmpty()) {
            // Generate a new request ID if not already present
            String requestId = java.util.UUID.randomUUID().toString();
            MDC.put("Request-ID", requestId);
        } else {
            // Retain the existing request ID
            MDC.put("Request-ID", existingRequestId);
        }
    }
}