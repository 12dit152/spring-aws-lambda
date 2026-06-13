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
public class CoreHrController {

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

    @GetMapping(value = "/api/v1/hris/employees", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getAllEmployees() {
        return ResponseEntity.ok(readResource("mock/core-hr/get-all-employees.json"));
    }

    @PostMapping(value = "/api/v1/hris/employees", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createEmployee() {
        return ResponseEntity.ok(readResource("mock/core-hr/create-employee.json"));
    }

    @PutMapping(value = "/api/v1/hris/employees/{id}/personaldetails", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateEmployeePersonalDetails(@PathVariable("id") String id) {
        return ResponseEntity.ok(readResource("mock/core-hr/update-employee-personal-details.json"));
    }

    @PutMapping(value = "/api/v1/hris/employees/{id}/jobdetails", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateEmployeeJobDetails(@PathVariable("id") String id) {
        return ResponseEntity.ok(readResource("mock/core-hr/update-employee-job-details.json"));
    }

    @GetMapping(value = "/api/v1/hris/employees/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getAnEmployee(@PathVariable("id") String id) {
        return ResponseEntity.ok(readResource("mock/core-hr/get-an-employee.json"));
    }

    @GetMapping(value = "/api/v1/hris/employees/updatefields", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getAllUpdateFields() {
        return ResponseEntity.ok(readResource("mock/core-hr/get-all-update-fields.json"));
    }

    @GetMapping(value = "/api/v1/hris/groups", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getAllGroups() {
        return ResponseEntity.ok(readResource("mock/core-hr/get-all-groups.json"));
    }

    @GetMapping(value = "/api/v1/hris/grouptypes", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getAllGroupTypes() {
        return ResponseEntity.ok(readResource("mock/core-hr/get-all-group-types.json"));
    }

    @GetMapping(value = "/api/v1/hris/departments", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getAllDepartments() {
        return ResponseEntity.ok(readResource("mock/core-hr/get-all-departments.json"));
    }

    @GetMapping(value = "/api/v1/hris/locations", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getAllLocations() {
        return ResponseEntity.ok(readResource("mock/core-hr/get-all-locations.json"));
    }

    @GetMapping(value = "/api/v1/hris/jobtitles", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getAllJobTitles() {
        return ResponseEntity.ok(readResource("mock/core-hr/get-all-job-titles.json"));
    }

    @GetMapping(value = "/api/v1/hris/currencies", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getAllCurrencies() {
        return ResponseEntity.ok(readResource("mock/core-hr/get-all-currencies.json"));
    }

    @GetMapping(value = "/api/v1/hris/noticeperiods", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getAllNoticeperiods() {
        return ResponseEntity.ok(readResource("mock/core-hr/get-all-noticeperiods.json"));
    }

    @GetMapping(value = "/api/v1/hris/exitreasons", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getAllExitreasons() {
        return ResponseEntity.ok(readResource("mock/core-hr/get-all-exitreasons.json"));
    }

    @PostMapping(value = "/api/v1/hris/employees/{id}/exitrequest", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createAnExitRequest(@PathVariable("id") String id) {
        return ResponseEntity.ok(readResource("mock/core-hr/create-an-exit-request.json"));
    }

    @PutMapping(value = "/api/v1/hris/employees/{id}/exitrequest", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateExitRequestForEmployee(@PathVariable("id") String id) {
        return ResponseEntity.ok(readResource("mock/core-hr/update-exit-request-for-employee.json"));
    }

}