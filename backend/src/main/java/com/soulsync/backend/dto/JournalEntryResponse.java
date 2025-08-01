package com.soulsync.backend.dto;

import java.time.LocalDateTime;

public class JournalEntryResponse {
    
    private String id;
    private String userId;
    private String content;
    private String mood;
    private String detectedMood;
    private Float sentimentScore;
    private Integer wordCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    public JournalEntryResponse() {}
    
    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    
    public String getMood() { return mood; }
    public void setMood(String mood) { this.mood = mood; }
    
    public String getDetectedMood() { return detectedMood; }
    public void setDetectedMood(String detectedMood) { this.detectedMood = detectedMood; }
    
    public Float getSentimentScore() { return sentimentScore; }
    public void setSentimentScore(Float sentimentScore) { this.sentimentScore = sentimentScore; }
    
    public Integer getWordCount() { return wordCount; }
    public void setWordCount(Integer wordCount) { this.wordCount = wordCount; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}