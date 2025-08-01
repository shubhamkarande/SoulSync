package com.soulsync.backend.repository;

import com.soulsync.backend.model.JournalEntry;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface JournalEntryRepository extends MongoRepository<JournalEntry, String> {
    
    List<JournalEntry> findByUserIdOrderByCreatedAtDesc(String userId);
    
    List<JournalEntry> findByUserIdAndCreatedAtBetweenOrderByCreatedAtDesc(
        String userId, LocalDateTime startDate, LocalDateTime endDate);
    
    Optional<JournalEntry> findByUserIdAndId(String userId, String id);
    
    List<JournalEntry> findByUserIdAndMood(String userId, String mood);
    
    @Query("{ 'userId': ?0, 'createdAt': { $gte: ?1, $lt: ?2 } }")
    Optional<JournalEntry> findByUserIdAndDateRange(String userId, LocalDateTime startOfDay, LocalDateTime endOfDay);
    
    long countByUserId(String userId);
}