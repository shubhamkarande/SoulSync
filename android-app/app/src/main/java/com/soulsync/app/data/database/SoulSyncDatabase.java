package com.soulsync.app.data.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.soulsync.app.data.dao.AffirmationDao;
import com.soulsync.app.data.dao.JournalEntryDao;
import com.soulsync.app.data.model.Affirmation;
import com.soulsync.app.data.model.JournalEntry;

@Database(
    entities = {JournalEntry.class, Affirmation.class},
    version = 1,
    exportSchema = false
)
public abstract class SoulSyncDatabase extends RoomDatabase {
    
    private static volatile SoulSyncDatabase INSTANCE;
    
    public abstract JournalEntryDao journalEntryDao();
    public abstract AffirmationDao affirmationDao();
    
    public static SoulSyncDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (SoulSyncDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                        context.getApplicationContext(),
                        SoulSyncDatabase.class,
                        "soulsync_database"
                    )
                    .fallbackToDestructiveMigration()
                    .build();
                }
            }
        }
        return INSTANCE;
    }
}