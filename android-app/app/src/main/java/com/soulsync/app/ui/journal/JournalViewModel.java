package com.soulsync.app.ui.journal;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.soulsync.app.data.model.JournalEntry;
import com.soulsync.app.data.repository.JournalRepository;

import java.util.Date;
import java.util.List;

public class JournalViewModel extends AndroidViewModel {
    
    private JournalRepository repository;
    private LiveData<List<JournalEntry>> allEntries;
    
    public JournalViewModel(@NonNull Application application) {
        super(application);
        repository = new JournalRepository(application);
        allEntries = repository.getAllEntries();
    }
    
    public LiveData<List<JournalEntry>> getAllEntries() {
        return allEntries;
    }
    
    public LiveData<JournalEntry> getEntryById(String id) {
        return repository.getEntryById(id);
    }
    
    public LiveData<JournalEntry> getEntryByDate(Date date) {
        return repository.getEntryByDate(date);
    }
    
    public LiveData<List<JournalEntry>> getEntriesBetweenDates(Date startDate, Date endDate) {
        return repository.getEntriesBetweenDates(startDate, endDate);
    }
    
    public LiveData<Integer> getEntryCount() {
        return repository.getEntryCount();
    }
    
    public void insertEntry(JournalEntry entry) {
        repository.insertEntry(entry);
    }
    
    public void updateEntry(JournalEntry entry) {
        repository.updateEntry(entry);
    }
    
    public void deleteEntry(JournalEntry entry) {
        repository.deleteEntry(entry);
    }
}