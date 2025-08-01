package com.soulsync.backend.controller;

import com.soulsync.backend.dto.JournalEntryRequest;
import com.soulsync.backend.dto.JournalEntryResponse;
import com.soulsync.backend.model.JournalEntry;
import com.soulsync.backend.service.JournalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/journal")
@CrossOrigin(origins = "*")
public class JournalController {
    
    @Autowired
    private JournalService journalService;
    
    @GetMapping("/entries")
    public ResponseEntity<List<JournalEntryResponse>> getAllEntries(
            @RequestParam String userId) {
        List<JournalEntryResponse> entries = journalService.getAllEntriesByUser(userId);
        return ResponseEntity.ok(entries);
    }
    
    @GetMapping("/entries/{id}")
    public ResponseEntity<JournalEntryResponse> getEntry(
            @PathVariable String id,
            @RequestParam String userId) {
        JournalEntryResponse entry = journalService.getEntryById(id, userId);
        return ResponseEntity.ok(entry);
    }
    
    @GetMapping("/entries/date/{date}")
    public ResponseEntity<JournalEntryResponse> getEntryByDate(
            @PathVariable LocalDate date,
            @RequestParam String userId) {
        JournalEntryResponse entry = journalService.getEntryByDate(date, userId);
        if (entry != null) {
            return ResponseEntity.ok(entry);
        }
        return ResponseEntity.notFound().build();
    }
    
    @PostMapping("/entries")
    public ResponseEntity<JournalEntryResponse> createEntry(
            @RequestBody JournalEntryRequest request) {
        JournalEntryResponse entry = journalService.createEntry(request);
        return ResponseEntity.ok(entry);
    }
    
    @PutMapping("/entries/{id}")
    public ResponseEntity<JournalEntryResponse> updateEntry(
            @PathVariable String id,
            @RequestBody JournalEntryRequest request) {
        JournalEntryResponse entry = journalService.updateEntry(id, request);
        return ResponseEntity.ok(entry);
    }
    
    @DeleteMapping("/entries/{id}")
    public ResponseEntity<Void> deleteEntry(
            @PathVariable String id,
            @RequestParam String userId) {
        journalService.deleteEntry(id, userId);
        return ResponseEntity.noContent().build();
    }
    
    @PostMapping("/entries/{id}/analyze")
    public ResponseEntity<JournalEntryResponse> analyzeEntry(
            @PathVariable String id,
            @RequestParam String userId) {
        JournalEntryResponse entry = journalService.analyzeEntryMood(id, userId);
        return ResponseEntity.ok(entry);
    }
}