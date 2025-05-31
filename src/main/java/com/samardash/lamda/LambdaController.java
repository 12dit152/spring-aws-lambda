package com.samardash.lamda;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/v1/")
public class LambdaController {
    private static final Logger log = LoggerFactory.getLogger(LambdaController.class);

    @RequestMapping(value = "/hello", produces = MediaType.APPLICATION_JSON_VALUE)
    public String hello() {
       log.info("Received request for /hello endpoint");
        return "{\"message\": \"Hello from AWS Lambda\"}";
    }
}
