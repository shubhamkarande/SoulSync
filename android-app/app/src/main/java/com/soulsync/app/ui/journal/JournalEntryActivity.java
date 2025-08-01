package com.soulsync.app.ui.journal;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.chip.Chip;
import com.soulsync.app.R;
import com.soulsync.app.data.model.JournalEntry;
import com.soulsync.app.data.model.Mood;
import com.soulsync.app.databinding.ActivityJournalEntryBinding;

import java.util.Date;

public class JournalEntryActivity extends AppCompatActivity {
    
    private ActivityJournalEntryBinding binding;
    private JournalViewModel viewModel;
    private JournalEntry currentEntry;
    private String selectedMood;
    private boolean isEditing = false;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        binding = ActivityJournalEntryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        
        viewModel = new ViewModelProvider(this).get(JournalViewModel.class);
        
        setupToolbar();
        setupMoodChips();
        setupTextWatcher();
        
        // Check if editing existing entry
        String entryId = getIntent().getStringExtra("entry_id");
        if (entryId != null) {
            isEditing = true;
            loadEntry(entryId);
        } else {
            currentEntry = new JournalEntry();
            binding.editTextContent.requestFocus();
        }
    }
    
    private void setupToolbar() {
        setSupportActionBar(binding.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(isEditing ? "Edit Entry" : "New Entry");
        }
    }
    
    private void setupMoodChips() {
        for (Mood mood : Mood.values()) {
            Chip chip = new Chip(this);
            chip.setText(mood.getEmoji() + " " + mood.getValue());
            chip.setCheckable(true);
            chip.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    selectedMood = mood.getValue();
                    // Uncheck other chips
                    for (int i = 0; i < binding.chipGroupMoods.getChildCount(); i++) {
                        Chip otherChip = (Chip) binding.chipGroupMoods.getChildAt(i);
                        if (otherChip != chip) {
                            otherChip.setChecked(false);
                        }
                    }
                } else if (selectedMood != null && selectedMood.equals(mood.getValue())) {
                    selectedMood = null;
                }
            });
            binding.chipGroupMoods.addView(chip);
        }
    }
    
    private void setupTextWatcher() {
        binding.editTextContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateWordCount(s.toString());
            }
            
            @Override
            public void afterTextChanged(Editable s) {}
        });
    }
    
    private void updateWordCount(String text) {
        int wordCount = text.trim().isEmpty() ? 0 : text.trim().split("\\s+").length;
        binding.textWordCount.setText(getString(R.string.journal_word_count, wordCount));
    }
    
    private void loadEntry(String entryId) {
        viewModel.getEntryById(entryId).observe(this, entry -> {
            if (entry != null) {
                currentEntry = entry;
                binding.editTextContent.setText(entry.getContent());
                
                if (entry.getMood() != null) {
                    selectedMood = entry.getMood();
                    selectMoodChip(entry.getMood());
                }
                
                updateWordCount(entry.getContent());
            }
        });
    }
    
    private void selectMoodChip(String moodValue) {
        for (int i = 0; i < binding.chipGroupMoods.getChildCount(); i++) {
            Chip chip = (Chip) binding.chipGroupMoods.getChildAt(i);
            String chipText = chip.getText().toString();
            if (chipText.contains(moodValue)) {
                chip.setChecked(true);
                break;
            }
        }
    }
    
    private void saveEntry() {
        String content = binding.editTextContent.getText().toString().trim();
        
        if (content.isEmpty()) {
            Toast.makeText(this, "Please write something before saving", Toast.LENGTH_SHORT).show();
            return;
        }
        
        currentEntry.setContent(content);
        currentEntry.setMood(selectedMood);
        
        if (isEditing) {
            currentEntry.setUpdatedAt(new Date());
            viewModel.updateEntry(currentEntry);
            Toast.makeText(this, "Entry updated", Toast.LENGTH_SHORT).show();
        } else {
            viewModel.insertEntry(currentEntry);
            Toast.makeText(this, "Entry saved", Toast.LENGTH_SHORT).show();
        }
        
        finish();
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.journal_entry_menu, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == android.R.id.home) {
            onBackPressed();
            return true;
        } else if (itemId == R.id.action_save) {
            saveEntry();
            return true;
        } else if (itemId == R.id.action_delete && isEditing) {
            deleteEntry();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    private void deleteEntry() {
        if (currentEntry != null) {
            viewModel.deleteEntry(currentEntry);
            Toast.makeText(this, "Entry deleted", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}