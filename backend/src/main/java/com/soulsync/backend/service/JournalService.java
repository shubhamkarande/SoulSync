package com.soulsync.backend.service;

import com.soulsync.backend.dto.JournalEntryRequest;
import com.soulsync.backend.dto.JournalEntryResponse;
import com.soulsync.backend.model.JournalEntry;
import com.soulsync.backend.repository.JournalEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JournalService {
    
    @Autowired
    private JournalEntryRepository journalEntryRepository;
    
    @Autowired
    private SentimentAnalysisService sentimentAnalysisService;
    
    public List<JournalEntryResponse> getAllEntriesByUser(String userId) {
        List<JournalEntry> entries = journalEntryRepository.findByUserIdOrderByCreatedAtDesc(userId);
        return entries.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }
    
    public JournalEntryResponse getEntryById(String id, String userId) {
        JournalEntry entry = journalEntryRepository.findByUserIdAndId(userId, id)
                .orElseThrow(() -> new RuntimeException("Entry not found"));
        return convertToResponse(entry);
    }
    
    public JournalEntryResponse getEntryByDate(LocalDate date, String userId) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(LocalTime.MAX);
        
        return journalEntryRepository.findByUserIdAndDateRange(userId, startOfDay, endOfDay)
                .map(this::convertToResponse)
                .orElse(null);
    }
    
    public JournalEntryResponse createEntry(JournalEntryRequest request) {
        JournalEntry entry = new JournalEntry();
        entry.setUserId(request.getUserId());
        entry.setContent(request.getContent());
        entry.setMood(request.getMood());
        
        // Analyze sentiment if content is provided
        if (request.getContent() != null && !request.getContent().trim().isEmpty()) {
            try {
                float sentimentScore = sentimentAnalysisService.analyzeSentiment(request.getContent());
                entry.setSentimentScore(sentimentScore);
                entry.setDetectedMood(mapSentimentToMood(sentimentScore));
            } catch (Exception e) {
                // Log error but don't fail the request
                System.err.println("Failed to analyze sentiment: " + e.getMessage());
            }
        }
        
        JournalEntry savedEntry = journalEntryRepository.save(entry);
        return convertToResponse(savedEntry);
    }
    
    public JournalEntryResponse updateEntry(String id, JournalEntryRequest request) {
        JournalEntry entry = journalEntryRepository.findByUserIdAndId(request.getUserId(), id)
                .orElseThrow(() -> new RuntimeException("Entry not found"));
        
        entry.setContent(request.getContent());
        entry.setMood(request.getMood());
        entry.setUpdatedAt(LocalDateTime.now());
        
        // Re-analyze sentiment if content changed
        if (request.getContent() != null && !request.getContent().trim().isEmpty()) {
            try {
                float sentimentScore = sentimentAnalysisService.analyzeSentiment(request.getContent());
                entry.setSentimentScore(sentimentScore);
                entry.setDetectedMood(mapSentimentToMood(sentimentScore));
            } catch (Exception e) {
                System.err.println("Failed to analyze sentiment: " + e.getMessage());
            }
        }
        
        JournalEntry savedEntry = journalEntryRepository.save(entry);
        return convertToResponse(savedEntry);
    }
    
    public void deleteEntry(String id, String userId) {
        JournalEntry entry = journalEntryRepository.findByUserIdAndId(userId, id)
                .orElseThrow(() -> new RuntimeException("Entry not found"));
        journalEntryRepository.delete(entry);
    }
    
    public JournalEntryResponse analyzeEntryMood(String id, String userId) {
        JournalEntry entry = journalEntryRepository.findByUserIdAndId(userId, id)
                .orElseThrow(() -> new RuntimeException("Entry not found"));
        
        if (entry.getContent() != null && !entry.getContent().trim().isEmpty()) {
            try {
                float sentimentScore = sentimentAnalysisService.analyzeSentiment(entry.getContent());
                entry.setSentimentScore(sentimentScore);
                entry.setDetectedMood(mapSentimentToMood(sentimentScore));
                entry.setUpdatedAt(LocalDateTime.now());
                
                JournalEntry savedEntry = journalEntryRepository.save(entry);
                return convertToResponse(savedEntry);
            } catch (Exception e) {
                throw new RuntimeException("Failed to analyze mood: " + e.getMessage());
            }
        }
        
        return convertToResponse(entry);
    }
    
    private String mapSentimentToMood(float sentimentScore) {
        if (sentimentScore > 0.6) return "happy";
        if (sentimentScore > 0.2) return "calm";
        if (sentimentScore > -0.2) return "neutral";
        if (sentimentScore > -0.6) return "sad";
        return "angry";
    }
    
    private JournalEntryResponse convertToResponse(JournalEntry entry) {
        JournalEntryResponse response = new JournalEntryResponse();
        response.setId(entry.getId());
        response.setUserId(entry.getUserId());
        response.setContent(entry.getContent());
        response.setMood(entry.getMood());
        response.setDetectedMood(entry.getDetectedMood());
        response.setSentimentScore(entry.getSentimentScore());
        response.setWordCount(entry.getWordCount());
        response.setCreatedAt(entry.getCreatedAt());
        response.setUpdatedAt(entry.getUpdatedAt());
        return response;
    }
}