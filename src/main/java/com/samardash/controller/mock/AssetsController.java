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
public class AssetsController {

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

    @GetMapping(value = "/api/v1/assets", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getAllAssets() {
        return ResponseEntity.ok(readResource("mock/assets/get-all-assets.json"));
    }

    @PutMapping(value = "/api/v1/assets/{id}/allocation", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateAssetAssignment(@PathVariable("id") String id) {
        return ResponseEntity.ok(readResource("mock/assets/update-asset-assignment.json"));
    }

    @GetMapping(value = "/api/v1/assets/types", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getAllAssetsType() {
        return ResponseEntity.ok(readResource("mock/assets/get-all-assets-type.json"));
    }

    @GetMapping(value = "/api/v1/assets/categories", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getAllAssetCategories() {
        return ResponseEntity.ok(readResource("mock/assets/get-all-asset-categories.json"));
    }

    @GetMapping(value = "/api/v1/assets/conditions", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getAllAssetConditions() {
        return ResponseEntity.ok(readResource("mock/assets/get-all-asset-conditions.json"));
    }

}