package com.soulsync.app.ui.journal;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.soulsync.app.databinding.FragmentJournalBinding;
import com.soulsync.app.ui.journal.adapter.JournalEntryAdapter;

import java.util.Date;

public class JournalFragment extends Fragment {
    
    private FragmentJournalBinding binding;
    private JournalViewModel viewModel;
    private JournalEntryAdapter adapter;
    
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentJournalBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
    
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        viewModel = new ViewModelProvider(this).get(JournalViewModel.class);
        
        setupRecyclerView();
        setupFab();
        observeData();
        checkTodayEntry();
    }
    
    private void setupRecyclerView() {
        adapter = new JournalEntryAdapter(entry -> {
            // Open entry for editing
            Intent intent = new Intent(getContext(), JournalEntryActivity.class);
            intent.putExtra("entry_id", entry.getId());
            startActivity(intent);
        });
        
        binding.recyclerViewEntries.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerViewEntries.setAdapter(adapter);
    }
    
    private void setupFab() {
        binding.fabNewEntry.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), JournalEntryActivity.class);
            startActivity(intent);
        });
    }
    
    private void observeData() {
        viewModel.getAllEntries().observe(getViewLifecycleOwner(), entries -> {
            adapter.submitList(entries);
            
            if (entries.isEmpty()) {
                binding.emptyState.setVisibility(View.VISIBLE);
                binding.recyclerViewEntries.setVisibility(View.GONE);
            } else {
                binding.emptyState.setVisibility(View.GONE);
                binding.recyclerViewEntries.setVisibility(View.VISIBLE);
            }
        });
        
        viewModel.getEntryCount().observe(getViewLifecycleOwner(), count -> {
            binding.textEntryCount.setText(String.format("%d entries", count));
        });
    }
    
    private void checkTodayEntry() {
        viewModel.getEntryByDate(new Date()).observe(getViewLifecycleOwner(), todayEntry -> {
            if (todayEntry != null) {
                binding.textTodayStatus.setText("You've reflected today ✨");
                binding.textTodayStatus.setVisibility(View.VISIBLE);
            } else {
                binding.textTodayStatus.setText("Start your daily reflection");
                binding.textTodayStatus.setVisibility(View.VISIBLE);
            }
        });
    }
    
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}