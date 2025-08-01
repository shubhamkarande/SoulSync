package com.soulsync.backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "affirmations")
public class Affirmation {
    
    @Id
    private String id;
    private String userId;
    private String journalEntryId;
    private String content;
    private String category;
    private boolean isFavorite;
    private LocalDateTime createdAt;
    
    public Affirmation() {
        this.createdAt = LocalDateTime.now();
        this.isFavorite = false;
    }
    
    public Affirmation(String content, String journalEntryId, String userId) {
        this();
        this.content = content;
        this.journalEntryId = journalEntryId;
        this.userId = userId;
    }
    
    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    
    public String getJournalEntryId() { return journalEntryId; }
    public void setJournalEntryId(String journalEntryId) { this.journalEntryId = journalEntryId; }
    
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    
    public boolean isFavorite() { return isFavorite; }
    public void setFavorite(boolean favorite) { this.isFavorite = favorite; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}