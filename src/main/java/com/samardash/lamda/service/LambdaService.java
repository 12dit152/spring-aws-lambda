package com.samardash.lamda.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class LambdaService {

    @Value("${backend.url}")
    private String backendUrl;

    @Value("${backend.api-key}")
    private String apiKey;

    public void logBackendDetails() {
        log.info("Backend URL: {}", backendUrl);
        log.info("API Key: {}", apiKey);
    }
}
