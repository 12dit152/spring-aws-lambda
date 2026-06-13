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
public class PsaController {

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

    @GetMapping(value = "/api/v1/psa/clients", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getAllClients() {
        return ResponseEntity.ok(readResource("mock/psa/get-all-clients.json"));
    }

    @PostMapping(value = "/api/v1/psa/clients", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createAClient() {
        return ResponseEntity.ok(readResource("mock/psa/create-a-client.json"));
    }

    @GetMapping(value = "/api/v1/psa/clients/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getAClient(@PathVariable("id") String id) {
        return ResponseEntity.ok(readResource("mock/psa/get-a-client.json"));
    }

    @PutMapping(value = "/api/v1/psa/clients/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateAClient(@PathVariable("id") String id) {
        return ResponseEntity.ok(readResource("mock/psa/update-a-client.json"));
    }

    @GetMapping(value = "/api/v1/psa/clients/{id}/billingroles", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getBillingroles(@PathVariable("id") String id) {
        return ResponseEntity.ok(readResource("mock/psa/get-billingroles.json"));
    }

    @GetMapping(value = "/api/v1/psa/projects/{projectId}/phases", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getProjectPhases(@PathVariable("projectId") String projectId) {
        return ResponseEntity.ok(readResource("mock/psa/get-project-phases.json"));
    }

    @PostMapping(value = "/api/v1/psa/projects/{projectId}/phases", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createProjectPhase(@PathVariable("projectId") String projectId) {
        return ResponseEntity.ok(readResource("mock/psa/create-project-phase.json"));
    }

    @PostMapping(value = "/api/v1/psa/projects", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createAProject() {
        return ResponseEntity.ok(readResource("mock/psa/create-a-project.json"));
    }

    @GetMapping(value = "/api/v1/psa/projects", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getAllProjects() {
        return ResponseEntity.ok(readResource("mock/psa/get-all-projects.json"));
    }

    @GetMapping(value = "/api/v1/psa/projects/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getAProject(@PathVariable("id") String id) {
        return ResponseEntity.ok(readResource("mock/psa/get-a-project.json"));
    }

    @PutMapping(value = "/api/v1/psa/projects/{projectId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateProjectDetails(@PathVariable("projectId") String projectId) {
        return ResponseEntity.ok(readResource("mock/psa/update-project-details.json"));
    }

    @GetMapping(value = "/api/v1/psa/projects/{projectId}/allocations", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getProjectAllocations(@PathVariable("projectId") String projectId) {
        return ResponseEntity.ok(readResource("mock/psa/get-project-allocations.json"));
    }

    @PostMapping(value = "/api/v1/psa/projects/{id}/allocations", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addAProjectAllocation(@PathVariable("id") String id) {
        return ResponseEntity.ok(readResource("mock/psa/add-a-project-allocation.json"));
    }

    @GetMapping(value = "/api/v1/psa/projects/{id}/timeentries", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getProjectTimesheetEnteries(@PathVariable("id") String id) {
        return ResponseEntity.ok(readResource("mock/psa/get-project-timesheet-enteries.json"));
    }

    @GetMapping(value = "/api/v1/psa/projects/{projectId}/tasks", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getProjectTasks(@PathVariable("projectId") String projectId) {
        return ResponseEntity.ok(readResource("mock/psa/get-project-tasks.json"));
    }

    @PostMapping(value = "/api/v1/psa/projects/{projectId}/tasks", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createNewProjectTask(@PathVariable("projectId") String projectId) {
        return ResponseEntity.ok(readResource("mock/psa/create-new-project-task.json"));
    }

    @PutMapping(value = "/api/v1/psa/projects/{projectId}/tasks/{taskId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateProjectTask(@PathVariable("projectId") String projectId, @PathVariable("taskId") String taskId) {
        return ResponseEntity.ok(readResource("mock/psa/update-project-task.json"));
    }

    @GetMapping(value = "/api/v1/psa/projects/{projectId}/tasks/{taskId}/timeentries", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getProjectTaskTimeEnteries(@PathVariable("projectId") String projectId, @PathVariable("taskId") String taskId) {
        return ResponseEntity.ok(readResource("mock/psa/get-project-task-time-enteries.json"));
    }

    @GetMapping(value = "/api/v1/psa/projects/{projectId}/tasks/{taskId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getProjectTaskAssignees(@PathVariable("projectId") String projectId, @PathVariable("taskId") String taskId) {
        return ResponseEntity.ok(readResource("mock/psa/get-project-task-assignees.json"));
    }

    @GetMapping(value = "/api/v1/psa/timeentries", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getTimesheetEnteries() {
        return ResponseEntity.ok(readResource("mock/psa/get-timesheet-enteries.json"));
    }

}