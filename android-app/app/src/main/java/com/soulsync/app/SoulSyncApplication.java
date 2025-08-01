package com.soulsync.app;

import android.app.Application;

public class SoulSyncApplication extends Application {
    
    @Override
    public void onCreate() {
        super.onCreate();
        
        // Initialize any global configurations here
        // For example: Crash reporting, analytics, etc.
    }
}