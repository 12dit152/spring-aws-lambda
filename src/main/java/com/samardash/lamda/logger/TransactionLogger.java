package com.samardash.lamda.logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static com.samardash.lamda.LambdaHandler.TRACE_ID;

@Slf4j
@Component
public class TransactionLogger extends OncePerRequestFilter {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        setRequestId();
        
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);

        try {
            filterChain.doFilter(requestWrapper, responseWrapper);
        } finally {
            logTransaction(requestWrapper, responseWrapper);
            responseWrapper.copyBodyToResponse();
        }
    }

    private void logTransaction(ContentCachingRequestWrapper request, ContentCachingResponseWrapper response) {
        try {
            MDC.put("http.status_code", String.valueOf(response.getStatus()));

            // Request details
            Map<String, Object> requestData = new HashMap<>();
            requestData.put("headers", getHeaders(request));
            byte[] requestContent = request.getContentAsByteArray();
            if (requestContent.length > 0) {
                requestData.put("body", new String(requestContent, StandardCharsets.UTF_8));
            }
            MDC.put("request", objectMapper.writeValueAsString(requestData));
            
            // Response details
            Map<String, Object> responseData = new HashMap<>();
            responseData.put("headers", getResponseHeaders(response));
            byte[] responseContent = response.getContentAsByteArray();
            if (responseContent.length > 0) {
                responseData.put("body", new String(responseContent, StandardCharsets.UTF_8));
            }
            MDC.put("response", objectMapper.writeValueAsString(responseData));
            
            log.info("TransactionLog for {}", request.getMethod() + " " + getFullPath(request));
        } catch (Exception e) {
            log.error("Error logging transaction", e);
        } finally {
            String traceId = MDC.get(TRACE_ID);
            MDC.clear();
            MDC.put(TRACE_ID, traceId);
        }
    }

    private Map<String, String> getHeaders(HttpServletRequest request) {
        Map<String, String> headers = new HashMap<>();
        request.getHeaderNames().asIterator().forEachRemaining(name -> headers.put(name, request.getHeader(name)));
        return headers;
    }

    private Map<String, String> getResponseHeaders(HttpServletResponse response) {
        Map<String, String> headers = new HashMap<>();
        response.getHeaderNames().forEach(name -> headers.put(name, response.getHeader(name)));
        return headers;
    }

    private String getFullPath(HttpServletRequest request) {
        String path = request.getRequestURI();
        String query = request.getQueryString();
        return query != null ? path + "?" + query : path;
    }

    private static void setRequestId() {
        String existingRequestId = MDC.get(TRACE_ID);
        if (existingRequestId == null || existingRequestId.isEmpty()) {
            String requestId = java.util.UUID.randomUUID().toString();
            MDC.put(TRACE_ID, requestId);
        }
    }
}