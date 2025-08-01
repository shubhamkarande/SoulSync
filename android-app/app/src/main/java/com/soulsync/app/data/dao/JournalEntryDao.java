package com.soulsync.app.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.soulsync.app.data.model.JournalEntry;

import java.util.Date;
import java.util.List;

@Dao
public interface JournalEntryDao {
    
    @Query("SELECT * FROM journal_entries ORDER BY createdAt DESC")
    LiveData<List<JournalEntry>> getAllEntries();
    
    @Query("SELECT * FROM journal_entries WHERE id = :id")
    LiveData<JournalEntry> getEntryById(String id);
    
    @Query("SELECT * FROM journal_entries WHERE DATE(createdAt/1000, 'unixepoch') = DATE(:date/1000, 'unixepoch')")
    LiveData<JournalEntry> getEntryByDate(Date date);
    
    @Query("SELECT * FROM journal_entries WHERE createdAt BETWEEN :startDate AND :endDate ORDER BY createdAt DESC")
    LiveData<List<JournalEntry>> getEntriesBetweenDates(Date startDate, Date endDate);
    
    @Query("SELECT * FROM journal_entries WHERE isSynced = 0")
    List<JournalEntry> getUnsyncedEntries();
    
    @Query("SELECT COUNT(*) FROM journal_entries")
    LiveData<Integer> getEntryCount();
    
    @Query("SELECT * FROM journal_entries WHERE mood = :mood ORDER BY createdAt DESC")
    LiveData<List<JournalEntry>> getEntriesByMood(String mood);
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertEntry(JournalEntry entry);
    
    @Update
    void updateEntry(JournalEntry entry);
    
    @Delete
    void deleteEntry(JournalEntry entry);
    
    @Query("DELETE FROM journal_entries WHERE id = :id")
    void deleteEntryById(String id);
    
    @Query("UPDATE journal_entries SET isSynced = 1 WHERE id = :id")
    void markAsSynced(String id);
}