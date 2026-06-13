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
public class SkillsController {

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

    @GetMapping(value = "/api/v1/skills", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getsAllSkills() {
        return ResponseEntity.ok(readResource("mock/skills/gets-all-skills.json"));
    }

    @GetMapping(value = "/api/v1/hris/employees/{id}/skills", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getEmployeeSkills(@PathVariable("id") String id) {
        return ResponseEntity.ok(readResource("mock/skills/get-employee-skills.json"));
    }

    @PostMapping(value = "/api/v1/hris/employees/{id}/skills", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addEmployeeSkills(@PathVariable("id") String id) {
        return ResponseEntity.ok(readResource("mock/skills/add-employee-skills.json"));
    }

}