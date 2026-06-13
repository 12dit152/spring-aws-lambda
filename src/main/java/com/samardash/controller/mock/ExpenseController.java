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
public class ExpenseController {

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

    @GetMapping(value = "/api/v1/expense/categories", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getExpenseCategories() {
        return ResponseEntity.ok(readResource("mock/expense/get-expense-categories.json"));
    }

    @GetMapping(value = "/api/v1/expense/claims", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getExpenseClaimDetails() {
        return ResponseEntity.ok(readResource("mock/expense/get-expense-claim-details.json"));
    }

    @GetMapping(value = "/api/v1/expensepolicies", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getAllExpensepolicies() {
        return ResponseEntity.ok(readResource("mock/expense/get-all-expensepolicies.json"));
    }

}