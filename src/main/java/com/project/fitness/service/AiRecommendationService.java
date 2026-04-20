package com.project.fitness.service;

import com.project.fitness.dto.AiResponseDetails;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Service
@Slf4j
public class AiRecommendationService {

    private final ChatClient chatClient;

    public AiRecommendationService(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    public AiResponseDetails getAiAdvice(String prompt) {
        try {
            // The .entity() method automatically asks the LLM for JSON
            // and maps it to your AiResponseDetails record!
            return chatClient.prompt()
                    .user(prompt)
                    .call()
                    .entity(AiResponseDetails.class);

        }  catch (Exception e) {
        log.error("AI API Call Failed: {}", e.getMessage());
        // Fallback response with all three fields as Lists!
        return new AiResponseDetails(
                "Great job on your workout! Stay hydrated.",
                List.of("Try to maintain a steady heart rate.", "Focus on breathing rhythm."),
                List.of("Consider a light walk tomorrow.", "Do some light yoga."),
                List.of("Always stretch before and after to prevent injury.") // <-- CHANGED TO LIST
        );
    }
    }
}