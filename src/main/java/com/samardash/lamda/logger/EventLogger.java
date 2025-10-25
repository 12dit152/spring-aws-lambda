package com.samardash.lamda.logger;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import static com.samardash.lamda.LambdaHandler.TRACE_ID;

@Slf4j
@Component
public class EventLogger {

    /**
     * {
     * "timestamp": "2025-10-25T12:58:12.300Z",
     * "level": "INFO",
     * "message": "Calling Payment API",
     * "http.method": "POST",
     * "http.path": "/api/pay",
     * "http.status_code": 200,
     * "http.response_time_ms": 50,
     * "traceId": "b9b4f5e1d3a24a67a91a567d89b0c3de",
     * "spanId": "f3d9b2c5a1e987b1",
     * "parentSpanId": "a2c8d4b9a5f342d3",
     * "service": "orders-service"
     * }
     */

    private final Map<String, EventData> activeEvents = new ConcurrentHashMap<>();

    public void startEvent(String eventName) {
        activeEvents.put(eventName, new EventData(eventName, System.currentTimeMillis()));
    }

    public void addEventData(String eventName, String key, String value) {
        EventData eventData = activeEvents.get(eventName);
        if (eventData != null) {
            eventData.data.put(key, value);
        }
    }

    public void endEvent(String eventName, int status) {
        EventData eventData = activeEvents.remove(eventName);
        if (eventData != null) {
            logEvent(eventData, status);
        }
    }

    private void logEvent(EventData eventData, int status) {
        try {
            // Save original MDC state
            Map<String, String> originalMDC = MDC.getCopyOfContextMap();

            // Clear MDC and set only EventLog fields
            MDC.clear();
            long elapsedTime = System.currentTimeMillis() - eventData.startTime;
            MDC.put("http.response_time_ms", String.valueOf(elapsedTime));
            MDC.put("http.status_code", String.valueOf(status));
            MDC.put("spanId", UUID.randomUUID().toString());

            // Keep requestId if it exists
            if (originalMDC != null && originalMDC.containsKey(TRACE_ID)) {
                MDC.put(TRACE_ID, originalMDC.get(TRACE_ID));
            }

            // Put event data in data field if present
            if (!eventData.data.isEmpty()) {
                eventData.data.forEach(MDC::put);
            }

            log.info("Event Log For : {}", eventData.eventName);

            // Restore original MDC
            MDC.clear();
            if (originalMDC != null) {
                originalMDC.forEach(MDC::put);
            }
        } catch (Exception e) {
            log.error("Error logging event", e);
        }
    }

    private static class EventData {
        final String eventName;
        final long startTime;
        final Map<String, String> data = new ConcurrentHashMap<>();

        EventData(String eventName, long startTime) {
            this.eventName = eventName;
            this.startTime = startTime;
        }
    }
}