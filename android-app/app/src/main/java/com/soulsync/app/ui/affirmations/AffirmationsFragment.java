package com.soulsync.app.ui.affirmations;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.soulsync.app.databinding.FragmentAffirmationsBinding;

public class AffirmationsFragment extends Fragment {
    
    private FragmentAffirmationsBinding binding;
    
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAffirmationsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
    
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        // TODO: Implement affirmations functionality
        setupPlaceholder();
    }
    
    private void setupPlaceholder() {
        binding.textTitle.setText("Daily Affirmations");
        binding.textDescription.setText("Your personalized affirmations will appear here after you write journal entries.");
    }
    
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}