package com.soulsync.app.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.soulsync.app.data.converter.DateConverter;

import java.util.Date;
import java.util.UUID;

@Entity(tableName = "journal_entries")
@TypeConverters(DateConverter.class)
public class JournalEntry {
    @PrimaryKey
    private String id;
    
    private String content;
    private Date createdAt;
    private Date updatedAt;
    private String mood;
    private String detectedMood;
    private float sentimentScore;
    private int wordCount;
    private boolean isSynced;
    private String userId;
    
    public JournalEntry() {
        this.id = UUID.randomUUID().toString();
        this.createdAt = new Date();
        this.updatedAt = new Date();
        this.isSynced = false;
        this.sentimentScore = 0.0f;
        this.wordCount = 0;
    }
    
    public JournalEntry(String content, String mood) {
        this();
        this.content = content;
        this.mood = mood;
        this.wordCount = content != null ? content.split("\\s+").length : 0;
    }
    
    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getContent() { return content; }
    public void setContent(String content) { 
        this.content = content;
        this.wordCount = content != null ? content.split("\\s+").length : 0;
        this.updatedAt = new Date();
    }
    
    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
    
    public Date getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }
    
    public String getMood() { return mood; }
    public void setMood(String mood) { 
        this.mood = mood;
        this.updatedAt = new Date();
    }
    
    public String getDetectedMood() { return detectedMood; }
    public void setDetectedMood(String detectedMood) { this.detectedMood = detectedMood; }
    
    public float getSentimentScore() { return sentimentScore; }
    public void setSentimentScore(float sentimentScore) { this.sentimentScore = sentimentScore; }
    
    public int getWordCount() { return wordCount; }
    public void setWordCount(int wordCount) { this.wordCount = wordCount; }
    
    public boolean isSynced() { return isSynced; }
    public void setSynced(boolean synced) { 
        this.isSynced = synced;
        if (synced) this.updatedAt = new Date();
    }
    
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
}