package com.samardash.lamda;

import org.slf4j.MDC;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.UUID;

@SpringBootApplication
public class LambdaApplication {
    public static void main(String[] args) {
        // Set startup request ID for application logs
        MDC.put("Request-ID", UUID.randomUUID().toString());
        SpringApplication.run(LambdaApplication.class, args);
        MDC.clear();
    }
}
