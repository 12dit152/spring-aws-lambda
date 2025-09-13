package com.samardash.lamda;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class LambdaService {

    private final OpenAiChatModel chatModel;

    public String serve(String message) {
        log.info("Received user message - {}", message);
        String finalMessage = message + " , Use three sentences maximum and keep the answer concise.";
        log.info("Modified user message - {}", finalMessage);
        if (message.length() > 100) {
            return "Message too long. Please limit to 100 characters.";
        }
        ChatResponse response;
        // @formatter:off
        try {
         response = ChatClient.create(this.chatModel).prompt()
                .options(OpenAiChatOptions.builder().build())
                .user(finalMessage)
                .call()
                .chatResponse();

        } catch (Exception e) {
            log.error("Error while calling AI model: {}", e.getMessage());
            return "Error: Unable to process the request at this time.";
        }
        // @formatter:on
        log.info("Full response - {}", response);
        if (null == response) {
            log.error("Invalid response from AI model");
            return "Error: Unable to process the request at this time.";
        } else {
            return response.getResult().getOutput().getText();
        }
    }
}
