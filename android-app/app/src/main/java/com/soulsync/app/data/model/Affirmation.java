package com.soulsync.app.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.soulsync.app.data.converter.DateConverter;

import java.util.Date;
import java.util.UUID;

@Entity(tableName = "affirmations")
@TypeConverters(DateConverter.class)
public class Affirmation {
    @PrimaryKey
    private String id;
    
    private String content;
    private String journalEntryId;
    private Date createdAt;
    private boolean isFavorite;
    private boolean isGenerated;
    private String category;
    private String userId;
    
    public Affirmation() {
        this.id = UUID.randomUUID().toString();
        this.createdAt = new Date();
        this.isFavorite = false;
        this.isGenerated = true;
    }
    
    public Affirmation(String content, String journalEntryId) {
        this();
        this.content = content;
        this.journalEntryId = journalEntryId;
    }
    
    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    
    public String getJournalEntryId() { return journalEntryId; }
    public void setJournalEntryId(String journalEntryId) { this.journalEntryId = journalEntryId; }
    
    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
    
    public boolean isFavorite() { return isFavorite; }
    public void setFavorite(boolean favorite) { this.isFavorite = favorite; }
    
    public boolean isGenerated() { return isGenerated; }
    public void setGenerated(boolean generated) { this.isGenerated = generated; }
    
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
}