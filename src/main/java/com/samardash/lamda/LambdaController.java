package com.samardash.lamda;

import com.amazonaws.serverless.proxy.model.AwsProxyRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.Enumeration;

@Slf4j
@RestController
@RequestMapping("/api/v1/")
public class LambdaController {

    @RequestMapping(value = "/hello", produces = MediaType.APPLICATION_JSON_VALUE)
    public String hello(ObjectMapper objectMapper) {
        log.info("Received request for /hello endpoint");

        ObjectNode response = objectMapper.createObjectNode();
        response.put("Version", "1.0.0");
        response.put("Developer", "Samar Dash");
        response.put("Message", "Hello from AWS Lambda");
        response.put("Home", "https://samardash.com");
        response.put("URI", "https://api.samardash.com/api/v1/hello");
        response.put("Sequence", "Route 53 -> API Gateway -> Lambda -> Spring Boot");
        response.put("Description", "This is a sample Spring Boot application running on AWS Serverless environment.");

        log.debug("Response: {}", response);
        return response.toPrettyString();
    }

    @PostMapping(value = "/post", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object post(HttpServletRequest request, @RequestParam String query, @RequestBody String body) {
        log.info("Received request for /post endpoint");
        log.info("Query Parameter :-> {}", query);
        log.info("Request Body :-> {}", body);
        log.info("Total headers found: {}", Collections.list(request.getHeaderNames()).size());

        // For localhost http header iteration
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String header = headerNames.nextElement();
            log.info("Local Header :-> {} -> {}", header, request.getHeader(header));
        }

        // Try reflection to access the underlying AWS event
        try {
            Field eventField = request.getClass().getDeclaredField("awsProxyRequest");
            eventField.setAccessible(true);
            AwsProxyRequest awsEvent = (AwsProxyRequest) eventField.get(request);
            awsEvent.getHeaders().forEach((header, value) ->
                    log.info("AWS Header :-> {} -> {}", header, value));
        } catch (Exception e) {
            log.warn("Could not access AWS headers: {}", e.getMessage());
        }

        return body;
    }

}