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
public class DocumentsController {

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

    @GetMapping(value = "/api/v1/hris/documents/types", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getTheDocumentTypes() {
        return ResponseEntity.ok(readResource("mock/documents/get-the-document-types.json"));
    }

    @GetMapping(value = "/api/v1/hris/employees/{id}/documenttypes/{id2}/documents", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getEmployeeDocuments(@PathVariable("id") String id, @PathVariable("id2") String id2) {
        return ResponseEntity.ok(readResource("mock/documents/get-employee-documents.json"));
    }

    @GetMapping(value = "/api/v1/hris/employees/{id}/documents/{id2}/attachment/{id3}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getEmployeeDocumentAttachmentDownloadUrl(@PathVariable("id") String id, @PathVariable("id2") String id2, @PathVariable("id3") String id3) {
        return ResponseEntity.ok(readResource("mock/documents/get-employee-document-attachment-download-url.json"));
    }

}