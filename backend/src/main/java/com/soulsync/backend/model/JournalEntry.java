package com.soulsync.backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "journal_entries")
public class JournalEntry {
    
    @Id
    private String id;
    private String userId;
    private String content;
    private String mood;
    private String detectedMood;
    private Float sentimentScore;
    private Integer wordCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    public JournalEntry() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
    
    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    
    public String getContent() { return content; }
    public void setContent(String content) { 
        this.content = content;
        this.wordCount = content != null ? content.split("\\s+").length : 0;
        this.updatedAt = LocalDateTime.now();
    }
    
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