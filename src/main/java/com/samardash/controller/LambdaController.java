package com.samardash.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.samardash.service.LambdaService;
import com.samardash.logger.EventLogger;
import com.samardash.logger.StatsLogger;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Collections;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/")
public class LambdaController {

    private final Environment environment;
    private final LambdaService lambdaService;
    private final StatsLogger statsLogger;
    private final EventLogger eventLogger;

    @RequestMapping(value = "/hello", produces = MediaType.APPLICATION_JSON_VALUE)
    public String hello(ObjectMapper objectMapper) {
        log.info("Received request for /hello endpoint");
        
        // Add custom stats data
        statsLogger.addData("environment", Arrays.toString(environment.getActiveProfiles()));

        // Example EventLogger usage
        eventLogger.startEvent("backend-call");
        eventLogger.addEventData("backend-call", "operation", "GET");
        eventLogger.addEventData("backend-call", "service", "backend-call");

        ObjectNode response = objectMapper.createObjectNode();
        response.put("status", "LIVE & SERVERLESS");
        response.put("developer", "Samar Dash");
        response.put("message", "Hello from AWS Lambda!");
        response.put("portfolio", "https://samardash.com");
        response.put("api_endpoint", "https://api.samardash.com/api/v1/hello");
        response.put("architecture", "Route 53 -> API Gateway -> Lambda -> Spring Boot");
        response.put("platform", "AWS Serverless + Java 21 + Spring Boot 3.5");
        response.put("environment", String.join(",", environment.getActiveProfiles()));
        response.put("powered_by", "AWS Lambda + GitHub Actions CI/CD");
        response.put("tech_stack", "Spring Boot 3.5 | Java 21 | Maven | ARM64");

        eventLogger.endEvent("backend-call", 200);
        log.debug("Response: {}", response);
        return response.toPrettyString();
    }

    @PostMapping(value = "/post", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object post(HttpServletRequest request, @RequestParam(required = false) String query, @RequestBody(required = false) String body, ObjectMapper objectMapper) {
        log.info("Received request for /post endpoint");
        lambdaService.logBackendDetails();
        
        ObjectNode response = objectMapper.createObjectNode();
        
        // Add query parameters
        ObjectNode args = objectMapper.createObjectNode();
        if (query != null) {
            args.put("query", query);
        }
        response.set("args", args);
        
        // Add headers
        ObjectNode headers = objectMapper.createObjectNode();
        Collections.list(request.getHeaderNames()).forEach(header ->
                headers.put(header, request.getHeader(header)));
        response.set("headers", headers);
        
        // Add request details
        response.put("origin", request.getRemoteAddr());
        response.put("url", request.getRequestURL().toString());
        response.put("environment", String.join(",", environment.getActiveProfiles()));
        
        // Add body if present
        if (body != null && !body.trim().isEmpty()) {
            try {
                response.set("json", objectMapper.readTree(body));
            } catch (Exception e) {
                response.put("data", body);
            }
        }
        
        return response;
    }

}