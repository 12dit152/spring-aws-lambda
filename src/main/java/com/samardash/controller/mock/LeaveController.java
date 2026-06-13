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
public class LeaveController {

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

    @GetMapping(value = "/api/v1/time/leaverequests", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getAllLeaveRequests() {
        return ResponseEntity.ok(readResource("mock/leave/get-all-leave-requests.json"));
    }

    @GetMapping(value = "/api/v1/time/leavetypes", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getAllLeaveTypes() {
        return ResponseEntity.ok(readResource("mock/leave/get-all-leave-types.json"));
    }

    @PostMapping(value = "/api/v1/time/leaverequests", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createLeaveRequest() {
        return ResponseEntity.ok(readResource("mock/leave/create-leave-request.json"));
    }

    @GetMapping(value = "/api/v1/time/leavebalance", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getAllLeaveBalance() {
        return ResponseEntity.ok(readResource("mock/leave/get-all-leave-balance.json"));
    }

    @GetMapping(value = "/api/v1/time/leaveplans", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getAllLeavePlans() {
        return ResponseEntity.ok(readResource("mock/leave/get-all-leave-plans.json"));
    }

}