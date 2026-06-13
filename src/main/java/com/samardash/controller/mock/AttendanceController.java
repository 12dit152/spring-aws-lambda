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
public class AttendanceController {

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

    @GetMapping(value = "/api/v1/time/attendance", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getAllAttendanceRecords() {
        return ResponseEntity.ok(readResource("mock/attendance/get-all-attendance-records.json"));
    }

    @PostMapping(value = "/v1/logs", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> pushAttendancePunches() {
        return ResponseEntity.ok(readResource("mock/attendance/push-attendance-punches.json"));
    }

    @GetMapping(value = "/api/v1/time/capturescheme", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getAllCaptureschemes() {
        return ResponseEntity.ok(readResource("mock/attendance/get-all-captureschemes.json"));
    }

    @GetMapping(value = "/api/v1/time/shiftpolicies", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getAllShiftPolicies() {
        return ResponseEntity.ok(readResource("mock/attendance/get-all-shift-policies.json"));
    }

    @GetMapping(value = "/api/v1/time/holidayscalendar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getAllHolidayCalendar() {
        return ResponseEntity.ok(readResource("mock/attendance/get-all-holiday-calendar.json"));
    }

    @GetMapping(value = "/api/v1/time/penalisationpolicies", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getAllTrackingpolicies() {
        return ResponseEntity.ok(readResource("mock/attendance/get-all-trackingpolicies.json"));
    }

    @GetMapping(value = "/api/v1/time/weeklyoffpolicies", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getAllWeeklyoffPolicies() {
        return ResponseEntity.ok(readResource("mock/attendance/get-all-weeklyoff-policies.json"));
    }

}