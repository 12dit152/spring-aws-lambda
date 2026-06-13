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
public class PayrollController {

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

    @GetMapping(value = "/api/v1/payroll/salarycomponents", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getSalaryComponents() {
        return ResponseEntity.ok(readResource("mock/payroll/get-salary-components.json"));
    }

    @GetMapping(value = "/api/v1/payroll/paygroups", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getAllPayGroups() {
        return ResponseEntity.ok(readResource("mock/payroll/get-all-pay-groups.json"));
    }

    @GetMapping(value = "/api/v1/payroll/paygroups/{payGroupId}/paycycles", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getAllPayCycles(@PathVariable("payGroupId") String payGroupId) {
        return ResponseEntity.ok(readResource("mock/payroll/get-all-pay-cycles.json"));
    }

    @GetMapping(value = "/api/v1/payroll/paygroups/{payGroupId}/paycycles/{payCycleId}/payregister", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getPayRegister(@PathVariable("payGroupId") String payGroupId, @PathVariable("payCycleId") String payCycleId) {
        return ResponseEntity.ok(readResource("mock/payroll/get-pay-register.json"));
    }

    @GetMapping(value = "/api/v1/payroll/paygroups/{payGroupId}/paycycles/{payCycleId}/paybatches", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getAllPayBatches(@PathVariable("payGroupId") String payGroupId, @PathVariable("payCycleId") String payCycleId) {
        return ResponseEntity.ok(readResource("mock/payroll/get-all-pay-batches.json"));
    }

    @GetMapping(value = "/api/v1/payroll/paygroups/{payGroupId}/paycycles/{payCycleId}/paybatches/{payBatchId}/payments", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getAllBatchPayments(@PathVariable("payGroupId") String payGroupId, @PathVariable("payCycleId") String payCycleId, @PathVariable("payBatchId") String payBatchId) {
        return ResponseEntity.ok(readResource("mock/payroll/get-all-batch-payments.json"));
    }

    @PutMapping(value = "/api/v1/payroll/paygroups/{payGroupId}/paycycles/{payCycleId}/paybatches/{payBatchId}/payments", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateThePaymentStatus(@PathVariable("payGroupId") String payGroupId, @PathVariable("payCycleId") String payCycleId, @PathVariable("payBatchId") String payBatchId) {
        return ResponseEntity.ok(readResource("mock/payroll/update-the-payment-status.json"));
    }

    @GetMapping(value = "/api/v1/time/payroll/paygrades", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getAllPayGrades() {
        return ResponseEntity.ok(readResource("mock/payroll/get-all-pay-grades.json"));
    }

    @GetMapping(value = "/api/v1/payroll/payband", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getAllPayBands() {
        return ResponseEntity.ok(readResource("mock/payroll/get-all-pay-bands.json"));
    }

    @GetMapping(value = "/api/v1/payroll/salaries", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getAllEmployeeSalaries() {
        return ResponseEntity.ok(readResource("mock/payroll/get-all-employee-salaries.json"));
    }

    @GetMapping(value = "/api/v1/payroll/employees/fnf", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getAllEmployeeFnfDetails() {
        return ResponseEntity.ok(readResource("mock/payroll/get-all-employee-fnf-details.json"));
    }

    @GetMapping(value = "/api/v1/payroll/bonustypes", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getAllBonusTypes() {
        return ResponseEntity.ok(readResource("mock/payroll/get-all-bonus-types.json"));
    }

}