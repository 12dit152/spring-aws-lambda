package com.samardash.lamda;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class EventLogger {

    public enum EventStatus {
        SUCCESS,
        FAILURE
    }

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Map<String, EventData> activeEvents = new ConcurrentHashMap<>();

    public void startEvent(String eventName) {
        activeEvents.put(eventName, new EventData(eventName, System.currentTimeMillis()));
    }

    public void addEventData(String eventName, String key, Object value) {
        EventData eventData = activeEvents.get(eventName);
        if (eventData != null) {
            eventData.data.put(key, value);
        }
    }

    public void endEvent(String eventName, EventStatus status) {
        EventData eventData = activeEvents.remove(eventName);
        if (eventData != null) {
            logEvent(eventData, status);
        }
    }

    private void logEvent(EventData eventData, EventStatus status) {
        try {
            // Save original MDC state
            Map<String, String> originalMDC = MDC.getCopyOfContextMap();
            
            // Clear MDC and set only EventLog fields
            MDC.clear();
            MDC.put("logType", "EventLog");
            
            long elapsedTime = System.currentTimeMillis() - eventData.startTime;
            MDC.put("elapsedTimeMs", String.valueOf(elapsedTime));
            MDC.put("status", status.name());
            
            // Keep requestId if it exists
            if (originalMDC != null && originalMDC.containsKey("requestId")) {
                MDC.put("requestId", originalMDC.get("requestId"));
            }
            
            // Put event data in data field if present
            if (!eventData.data.isEmpty()) {
                MDC.put("data", objectMapper.writeValueAsString(eventData.data));
            }
            
            log.info("Event log for " + eventData.eventName);
            
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
        final Map<String, Object> data = new ConcurrentHashMap<>();

        EventData(String eventName, long startTime) {
            this.eventName = eventName;
            this.startTime = startTime;
        }
    }
}