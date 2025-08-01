package com.soulsync.app.data.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.soulsync.app.data.dao.JournalEntryDao;
import com.soulsync.app.data.database.SoulSyncDatabase;
import com.soulsync.app.data.model.JournalEntry;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class JournalRepository {
    private JournalEntryDao journalEntryDao;
    private LiveData<List<JournalEntry>> allEntries;
    private ExecutorService executor;
    
    public JournalRepository(Application application) {
        SoulSyncDatabase database = SoulSyncDatabase.getDatabase(application);
        journalEntryDao = database.journalEntryDao();
        allEntries = journalEntryDao.getAllEntries();
        executor = Executors.newFixedThreadPool(4);
    }
    
    public LiveData<List<JournalEntry>> getAllEntries() {
        return allEntries;
    }
    
    public LiveData<JournalEntry> getEntryById(String id) {
        return journalEntryDao.getEntryById(id);
    }
    
    public LiveData<JournalEntry> getEntryByDate(Date date) {
        return journalEntryDao.getEntryByDate(date);
    }
    
    public LiveData<List<JournalEntry>> getEntriesBetweenDates(Date startDate, Date endDate) {
        return journalEntryDao.getEntriesBetweenDates(startDate, endDate);
    }
    
    public LiveData<List<JournalEntry>> getEntriesByMood(String mood) {
        return journalEntryDao.getEntriesByMood(mood);
    }
    
    public LiveData<Integer> getEntryCount() {
        return journalEntryDao.getEntryCount();
    }
    
    public void insertEntry(JournalEntry entry) {
        executor.execute(() -> journalEntryDao.insertEntry(entry));
    }
    
    public void updateEntry(JournalEntry entry) {
        executor.execute(() -> journalEntryDao.updateEntry(entry));
    }
    
    public void deleteEntry(JournalEntry entry) {
        executor.execute(() -> journalEntryDao.deleteEntry(entry));
    }
    
    public void deleteEntryById(String id) {
        executor.execute(() -> journalEntryDao.deleteEntryById(id));
    }
    
    public void markAsSynced(String id) {
        executor.execute(() -> journalEntryDao.markAsSynced(id));
    }
    
    public List<JournalEntry> getUnsyncedEntries() {
        return journalEntryDao.getUnsyncedEntries();
    }
}