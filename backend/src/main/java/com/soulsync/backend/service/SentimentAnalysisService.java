package com.soulsync.backend.service;

import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.completion.chat.ChatMessageRole;
import com.theokanning.openai.service.OpenAiService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class SentimentAnalysisService {
    
    @Value("${openai.api.key:}")
    private String openAiApiKey;
    
    private OpenAiService openAiService;
    
    public float analyzeSentiment(String text) {
        if (openAiApiKey == null || openAiApiKey.isEmpty()) {
            // Fallback to simple keyword-based analysis
            return performSimpleSentimentAnalysis(text);
        }
        
        try {
            if (openAiService == null) {
                openAiService = new OpenAiService(openAiApiKey);
            }
            
            String prompt = "Analyze the sentiment of the following journal entry and return only a number between -1.0 (very negative) and 1.0 (very positive). Do not include any explanation, just the number:\n\n" + text;
            
            ChatCompletionRequest request = ChatCompletionRequest.builder()
                    .model("gpt-3.5-turbo")
                    .messages(Arrays.asList(
                            new ChatMessage(ChatMessageRole.USER.value(), prompt)
                    ))
                    .maxTokens(10)
                    .temperature(0.0)
                    .build();
            
            String response = openAiService.createChatCompletion(request)
                    .getChoices().get(0).getMessage().getContent().trim();
            
            return Float.parseFloat(response);
            
        } catch (Exception e) {
            System.err.println("OpenAI sentiment analysis failed, using fallback: " + e.getMessage());
            return performSimpleSentimentAnalysis(text);
        }
    }
    
    private float performSimpleSentimentAnalysis(String text) {
        String lowerText = text.toLowerCase();
        
        // Simple keyword-based sentiment analysis
        String[] positiveWords = {"happy", "joy", "good", "great", "amazing", "wonderful", 
                                 "excited", "love", "peaceful", "calm", "grateful", "blessed"};
        String[] negativeWords = {"sad", "angry", "frustrated", "terrible", "awful", "hate", 
                                 "depressed", "anxious", "worried", "stressed", "upset", "mad"};
        
        int positiveCount = 0;
        int negativeCount = 0;
        
        for (String word : positiveWords) {
            if (lowerText.contains(word)) {
                positiveCount++;
            }
        }
        
        for (String word : negativeWords) {
            if (lowerText.contains(word)) {
                negativeCount++;
            }
        }
        
        if (positiveCount == 0 && negativeCount == 0) {
            return 0.0f; // Neutral
        }
        
        float score = (float) (positiveCount - negativeCount) / (positiveCount + negativeCount);
        return Math.max(-1.0f, Math.min(1.0f, score));
    }
}