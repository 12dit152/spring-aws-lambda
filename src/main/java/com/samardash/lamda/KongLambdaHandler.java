package com.samardash.lamda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class KongLambdaHandler implements RequestHandler<Map<String, Object>, Map<String, Object>> {

    @Autowired
    private LambdaController lambdaController;
    
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public Map<String, Object> handleRequest(Map<String, Object> input, Context context) {
        try {
            String response = lambdaController.hello(objectMapper);
            return Map.of(
                "statusCode", 200,
                "body", response,
                "headers", Map.of("Content-Type", "application/json")
            );
        } catch (Exception e) {
            context.getLogger().log("Error: " + e.getMessage());
            return Map.of(
                "statusCode", 500,
                "body", "{\"error\":\"Internal Server Error\",\"message\":\"" + e.getMessage() + "\"}",
                "headers", Map.of("Content-Type", "application/json")
            );
        }
    }
}