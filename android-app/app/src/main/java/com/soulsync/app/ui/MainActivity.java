package com.soulsync.app.ui;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.soulsync.app.R;
import com.soulsync.app.databinding.ActivityMainBinding;
import com.soulsync.app.ui.affirmations.AffirmationsFragment;
import com.soulsync.app.ui.calendar.CalendarFragment;
import com.soulsync.app.ui.journal.JournalFragment;
import com.soulsync.app.ui.settings.SettingsFragment;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    
    private ActivityMainBinding binding;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        
        setupBottomNavigation();
        
        // Load default fragment
        if (savedInstanceState == null) {
            loadFragment(new JournalFragment());
        }
    }
    
    private void setupBottomNavigation() {
        binding.bottomNavigation.setOnNavigationItemSelectedListener(this);
    }
    
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        
        int itemId = item.getItemId();
        if (itemId == R.id.nav_journal) {
            fragment = new JournalFragment();
        } else if (itemId == R.id.nav_calendar) {
            fragment = new CalendarFragment();
        } else if (itemId == R.id.nav_affirmations) {
            fragment = new AffirmationsFragment();
        } else if (itemId == R.id.nav_settings) {
            fragment = new SettingsFragment();
        }
        
        return loadFragment(fragment);
    }
    
    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
            return true;
        }
        return false;
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}