package com.samardash.controller.mock;

import org.springframework.web.bind.annotation.*;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.FileCopyUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;

import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/mock")
public class BgvApisController {

    private String readResource(String path) {
        try {
            Resource resource = new ClassPathResource(path);
            try (Reader reader = new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8)) {
                return FileCopyUtils.copyToString(reader);
            }
        } catch (Exception e) {
            return "{}";
        }
    }

    @GetMapping(value = "/api/v1/hris/bgv/{id}/requests", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getAllBgvRequests(@PathVariable("id") String id) {
        return ResponseEntity.ok(readResource("mock/bgv-apis/get-all-bgv-requests.json"));
    }

    @PutMapping(value = "/api/v1/hris/bgv/{bgvId}/requests/{requestId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addBgvRequestReport(@PathVariable("bgvId") String bgvId, @PathVariable("requestId") String requestId) {
        return ResponseEntity.ok(readResource("mock/bgv-apis/add-bgv-request-report.json"));
    }

}