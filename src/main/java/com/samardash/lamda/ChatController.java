package com.samardash.lamda;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@AllArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/")
public class ChatController {

    private final LambdaService lambdaService;

    @GetMapping("/generate")
    public String generate(@RequestParam(value = "message", defaultValue = "Hi") String message) {
        return lambdaService.serve(message);
    }
}
