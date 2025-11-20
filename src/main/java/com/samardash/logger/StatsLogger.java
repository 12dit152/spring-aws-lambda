package com.samardash.logger;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import static com.samardash.lambda.LambdaHandler.TRACE_ID;

@Slf4j
@Component
public class StatsLogger implements HandlerInterceptor {

    private static final String START_TIME_ATTR = "statsStartTime";

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) {
        request.setAttribute(START_TIME_ATTR, System.currentTimeMillis());
        return true;
    }

    @Override
    public void afterCompletion(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler, @Nullable Exception ex) {
        Long startTime = (Long) request.getAttribute(START_TIME_ATTR);
        if (startTime != null) {
            long elapsedTime = System.currentTimeMillis() - startTime;
            logStats(request, response, elapsedTime);
        }
    }

    private void logStats(HttpServletRequest request, HttpServletResponse response, long elapsedTime) {
        try {
            MDC.put("http.method", request.getMethod());
            MDC.put("http.path", request.getRequestURI());
            MDC.put("http.query", request.getQueryString());
            MDC.put("service", request.getRequestURI().replaceFirst("/", "").replaceAll("/", "-"));
            MDC.put("http.status_code", String.valueOf(response.getStatus()));
            MDC.put("http.response_time_ms", String.valueOf(elapsedTime));
            MDC.put("client.ip", getClientIp(request));
            log.info("Stats Log For : {}", request.getMethod() + " " +request.getRequestURI());
        } catch (Exception e) {
            log.error("Error logging stats {}", e.getMessage());
        } finally {
            String traceId = MDC.get(TRACE_ID);
            MDC.clear();
            MDC.put(TRACE_ID, traceId);
        }
    }

    private String getClientIp(HttpServletRequest request) {
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isEmpty()) {
            return xForwardedFor.split(",")[0].trim();
        }
        return request.getRemoteAddr();
    }

    public void addData(String key, String value) {
        MDC.put(key, value);
    }
}