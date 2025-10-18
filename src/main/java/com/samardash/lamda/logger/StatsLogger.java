package com.samardash.lamda.logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class StatsLogger implements HandlerInterceptor {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final String START_TIME_ATTR = "statsStartTime";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        request.setAttribute(START_TIME_ATTR, System.currentTimeMillis());
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        Long startTime = (Long) request.getAttribute(START_TIME_ATTR);
        if (startTime != null) {
            long elapsedTime = System.currentTimeMillis() - startTime;
            logStats(request, response, handler, elapsedTime, ex);
        }
    }

    private void logStats(HttpServletRequest request, HttpServletResponse response, Object handler, long elapsedTime, Exception ex) {
        try {
            String operationName = request.getMethod() + " " + request.getRequestURI();
            
            MDC.put("logType", "StatsLog");
            MDC.put("operationName", operationName);
            MDC.put("statusCode", String.valueOf(response.getStatus()));
            MDC.put("elapsedTimeMs", String.valueOf(elapsedTime));
            MDC.put("clientIp", getClientIp(request));

            Map<String, Object> data = new HashMap<>();

            // Add only custom fields from MDC
            Map<String, String> mdcData = MDC.getCopyOfContextMap();
            if (mdcData != null) {
                mdcData.entrySet().stream()
                        .filter(entry -> !entry.getKey().equals("logType") &&
                                !entry.getKey().equals("requestId") &&
                                !entry.getKey().equals("operationName") &&
                                !entry.getKey().equals("statusCode") &&
                                !entry.getKey().equals("elapsedTimeMs") &&
                                !entry.getKey().equals("clientIp"))
                        .forEach(entry -> {
                            data.put(entry.getKey(), entry.getValue());
                            MDC.remove(entry.getKey()); // Remove from MDC immediately
                        });
            }

            if (!data.isEmpty()) {
                MDC.put("data", objectMapper.writeValueAsString(data));
            }
            log.info("Stats log for " + operationName);
        } catch (Exception e) {
            log.error("Error logging stats", e);
        } finally {
            MDC.remove("logType");
            MDC.remove("operationName");
            MDC.remove("statusCode");
            MDC.remove("elapsedTimeMs");
            MDC.remove("clientIp");
            MDC.remove("data");

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