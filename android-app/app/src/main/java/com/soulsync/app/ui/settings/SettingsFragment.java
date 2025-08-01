package com.soulsync.app.ui.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.soulsync.app.databinding.FragmentSettingsBinding;

public class SettingsFragment extends Fragment {
    
    private FragmentSettingsBinding binding;
    
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
    
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        // TODO: Implement settings functionality
        setupPlaceholder();
    }
    
    private void setupPlaceholder() {
        binding.textTitle.setText("Settings");
        binding.textDescription.setText("App settings and preferences will be available here.");
    }
    
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}