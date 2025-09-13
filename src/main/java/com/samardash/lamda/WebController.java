package com.samardash.lamda;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    @GetMapping("/chat")
    public String index() {
        return "index.html";
    }
}