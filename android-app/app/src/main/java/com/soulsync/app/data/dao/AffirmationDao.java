package com.soulsync.app.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.soulsync.app.data.model.Affirmation;

import java.util.List;

@Dao
public interface AffirmationDao {
    
    @Query("SELECT * FROM affirmations ORDER BY createdAt DESC")
    LiveData<List<Affirmation>> getAllAffirmations();
    
    @Query("SELECT * FROM affirmations WHERE isFavorite = 1 ORDER BY createdAt DESC")
    LiveData<List<Affirmation>> getFavoriteAffirmations();
    
    @Query("SELECT * FROM affirmations WHERE journalEntryId = :journalEntryId")
    LiveData<List<Affirmation>> getAffirmationsByJournalEntry(String journalEntryId);
    
    @Query("SELECT * FROM affirmations WHERE id = :id")
    LiveData<Affirmation> getAffirmationById(String id);
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertAffirmation(Affirmation affirmation);
    
    @Update
    void updateAffirmation(Affirmation affirmation);
    
    @Delete
    void deleteAffirmation(Affirmation affirmation);
    
    @Query("UPDATE affirmations SET isFavorite = :isFavorite WHERE id = :id")
    void updateFavoriteStatus(String id, boolean isFavorite);
}