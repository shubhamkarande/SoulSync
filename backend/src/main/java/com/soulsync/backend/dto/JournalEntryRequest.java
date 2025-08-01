package com.soulsync.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class JournalEntryRequest {
    
    @NotNull
    @NotBlank
    private String userId;
    
    @NotNull
    @NotBlank
    private String content;
    
    private String mood;
    
    public JournalEntryRequest() {}
    
    // Getters and Setters
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    
    public String getMood() { return mood; }
    public void setMood(String mood) { this.mood = mood; }
}