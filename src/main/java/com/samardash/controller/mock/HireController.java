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
public class HireController {

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

    @GetMapping(value = "/api/v1/hire/jobboards", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getAllJobBoards() {
        return ResponseEntity.ok(readResource("mock/hire/get-all-job-boards.json"));
    }

    @GetMapping(value = "/api/v1/hire/jobs", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getJobDetails() {
        return ResponseEntity.ok(readResource("mock/hire/get-job-details.json"));
    }

    @GetMapping(value = "/api/v1/hire/jobs/{jobId}/applicationfields", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getJobApplicationFields(@PathVariable("jobId") String jobId) {
        return ResponseEntity.ok(readResource("mock/hire/get-job-application-fields.json"));
    }

    @GetMapping(value = "/api/v1/hire/jobs/{jobId}/candidates", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getCandidateDetails(@PathVariable("jobId") String jobId) {
        return ResponseEntity.ok(readResource("mock/hire/get-candidate-details.json"));
    }

    @PutMapping(value = "/api/v1/hire/jobs/{id}/candidate/{id2}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateACandiate(@PathVariable("id") String id, @PathVariable("id2") String id2) {
        return ResponseEntity.ok(readResource("mock/hire/update-a-candiate.json"));
    }

    @PostMapping(value = "/api/v1/hire/jobs/{id}/candidate/{id2}/notes", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addCandidateNotes(@PathVariable("id") String id, @PathVariable("id2") String id2) {
        return ResponseEntity.ok(readResource("mock/hire/add-candidate-notes.json"));
    }

    @PostMapping(value = "/api/v1/v1/hire/jobs/{jobId}/candidate", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createNewCandidate(@PathVariable("jobId") String jobId) {
        return ResponseEntity.ok(readResource("mock/hire/create-new-candidate.json"));
    }

    @GetMapping(value = "/api/v1/hire/jobs/{jobId}/candidate/{candidateId}/interviews", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> fetchCandidateInterviews(@PathVariable("jobId") String jobId, @PathVariable("candidateId") String candidateId) {
        return ResponseEntity.ok(readResource("mock/hire/fetch-candidate-interviews.json"));
    }

    @GetMapping(value = "/api/v1/hire/jobs/{jobId}/candidate/{candidateId}/scorecards", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getCandidateScorecard(@PathVariable("jobId") String jobId, @PathVariable("candidateId") String candidateId) {
        return ResponseEntity.ok(readResource("mock/hire/get-candidate-scorecard.json"));
    }

    @GetMapping(value = "/api/v1/hire/preboarding/candiates", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getAllPreboardingCandidates() {
        return ResponseEntity.ok(readResource("mock/hire/get-all-preboarding-candidates.json"));
    }

    @PostMapping(value = "/api/v1/hire/preboarding/candidates", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> postAPreboardingCandidate() {
        return ResponseEntity.ok(readResource("mock/hire/post-a-preboarding-candidate.json"));
    }

    @PutMapping(value = "/api/v1/hire/preboarding/candidates/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateAPreboardingCandidates(@PathVariable("id") String id) {
        return ResponseEntity.ok(readResource("mock/hire/update-a-preboarding-candidates.json"));
    }

}