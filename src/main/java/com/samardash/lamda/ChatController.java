package com.samardash.lamda;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/")
public class ChatController {

    private final OpenAiChatModel chatModel;

    @GetMapping("/generate")
    public String generate(@RequestParam(value = "message", defaultValue = "Hi") String message) {

        log.info("Received user message - {}", message);
        String finalMessage = message + " , Use three sentences maximum and keep the answer concise.";
        log.info("Modified user message - {}", finalMessage);

        if (message.length() > 100) {
            return "Message too long. Please limit to 100 characters.";
        }

        // @formatter:off
        ChatResponse response = ChatClient.create(this.chatModel).prompt()
                .options(OpenAiChatOptions.builder().build())
                .user(finalMessage)
                .call()
                .chatResponse();
        // @formatter:on
        log.info("Full response - {}", response);

        if (null == response) {
            log.error("Invalid response from AI model");
            return "Error: Invalid response from AI model";
        } else {
            return response.getResult().getOutput().getText();
        }
    }

}
