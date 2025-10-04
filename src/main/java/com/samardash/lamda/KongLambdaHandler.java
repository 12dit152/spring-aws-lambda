package com.samardash.lamda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;

@Slf4j
@Component
public class KongLambdaHandler implements RequestHandler<Map<String, Object>, Map<String, Object>> {

    @Autowired
    private LambdaController lambdaController;
    
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public Map<String, Object> handleRequest(Map<String, Object> input, Context context) {
        // Set correlation ID from Lambda context or generate new one
        String requestId = context.getAwsRequestId();
        if (requestId == null || requestId.isEmpty()) {
            requestId = UUID.randomUUID().toString();
        }
        MDC.put("Request-ID", requestId);
        
        try {
            log.info("Kong Lambda handler invoked with input: {}", input);
            String response = lambdaController.hello(objectMapper);
            log.info("Successfully processed request");
            return Map.of(
                "statusCode", 200,
                "body", response,
                "headers", Map.of(
                    "Content-Type", "application/json",
                    "X-Request-ID", requestId
                )
            );
        } catch (Exception e) {
            log.error("Error processing Kong Lambda request: {}", e.getMessage(), e);
            return Map.of(
                "statusCode", 500,
                "body", "{\"error\":\"Internal Server Error\",\"message\":\"" + e.getMessage() + "\"}",
                "headers", Map.of(
                    "Content-Type", "application/json",
                    "Request-ID", requestId
                )
            );
        } finally {
            MDC.clear();
        }
    }
}