package com.samardash.lamda;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@RestController
@RequestMapping("/api/v1/")
public class LambdaController {
    private static final Logger log = LoggerFactory.getLogger(LambdaController.class);

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
}