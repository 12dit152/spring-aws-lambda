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
public class PmsController {

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

    @GetMapping(value = "/api/v1/pms/timeframes", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getTimeFrameList() {
        return ResponseEntity.ok(readResource("mock/pms/get-time-frame-list.json"));
    }

    @GetMapping(value = "/api/v1/pms/goals", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getGoalList() {
        return ResponseEntity.ok(readResource("mock/pms/get-goal-list.json"));
    }

    @PutMapping(value = "/api/v1/pms/goals/{goalId}/progress", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateGoalProgress(@PathVariable("goalId") String goalId) {
        return ResponseEntity.ok(readResource("mock/pms/update-goal-progress.json"));
    }

    @GetMapping(value = "/api/v1/pms/badges", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getBadgeList() {
        return ResponseEntity.ok(readResource("mock/pms/get-badge-list.json"));
    }

    @GetMapping(value = "/api/v1/pms/praise", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getPraiseList() {
        return ResponseEntity.ok(readResource("mock/pms/get-praise-list.json"));
    }

    @PostMapping(value = "/api/v1/pms/praise", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createPraise() {
        return ResponseEntity.ok(readResource("mock/pms/create-praise.json"));
    }

    @GetMapping(value = "/api/v1/pms/reviewgroups", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getReviewGroupList() {
        return ResponseEntity.ok(readResource("mock/pms/get-review-group-list.json"));
    }

    @GetMapping(value = "/api/v1/pms/reviewcycles", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getReviewGroupCycleList() {
        return ResponseEntity.ok(readResource("mock/pms/get-review-group-cycle-list.json"));
    }

    @GetMapping(value = "/api/v1/pms/reviews", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getEmployeeReviewList() {
        return ResponseEntity.ok(readResource("mock/pms/get-employee-review-list.json"));
    }

}