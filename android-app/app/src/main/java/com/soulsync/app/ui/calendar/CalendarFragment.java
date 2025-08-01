package com.soulsync.app.ui.calendar;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.soulsync.app.data.model.JournalEntry;
import com.soulsync.app.databinding.FragmentCalendarBinding;
import com.soulsync.app.ui.journal.JournalEntryActivity;
import com.soulsync.app.ui.journal.JournalViewModel;

import java.util.Calendar;
import java.util.Date;

public class CalendarFragment extends Fragment implements OnDateSelectedListener {
    
    private FragmentCalendarBinding binding;
    private JournalViewModel viewModel;
    private CalendarDay selectedDate;
    
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCalendarBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
    
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        viewModel = new ViewModelProvider(this).get(JournalViewModel.class);
        
        setupCalendar();
        observeEntries();
        setupSelectedDateView();
    }
    
    private void setupCalendar() {
        binding.calendarView.setOnDateChangedListener(this);
        binding.calendarView.setSelectedDate(CalendarDay.today());
        selectedDate = CalendarDay.today();
        
        // Set calendar to current month
        binding.calendarView.setCurrentDate(CalendarDay.today());
    }
    
    private void observeEntries() {
        // Get entries for current month and mark calendar
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 1);
        Date startOfMonth = cal.getTime();
        
        cal.add(Calendar.MONTH, 1);
        cal.add(Calendar.DAY_OF_MONTH, -1);
        Date endOfMonth = cal.getTime();
        
        viewModel.getEntriesBetweenDates(startOfMonth, endOfMonth)
            .observe(getViewLifecycleOwner(), entries -> {
                // Add decorators for days with entries
                for (JournalEntry entry : entries) {
                    Calendar entryCal = Calendar.getInstance();
                    entryCal.setTime(entry.getCreatedAt());
                    CalendarDay day = CalendarDay.from(entryCal);
                    
                    // Add mood-based decorator
                    if (entry.getMood() != null) {
                        binding.calendarView.addDecorator(
                            new MoodDecorator(day, entry.getMood())
                        );
                    }
                }
            });
    }
    
    private void setupSelectedDateView() {
        loadEntryForDate(selectedDate);
        
        binding.buttonNewEntry.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), JournalEntryActivity.class);
            startActivity(intent);
        });
        
        binding.buttonEditEntry.setOnClickListener(v -> {
            // This will be set when an entry exists for the selected date
        });
    }
    
    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, 
                              @NonNull CalendarDay date, boolean selected) {
        selectedDate = date;
        loadEntryForDate(date);
    }
    
    private void loadEntryForDate(CalendarDay calendarDay) {
        Calendar cal = Calendar.getInstance();
        cal.set(calendarDay.getYear(), calendarDay.getMonth(), calendarDay.getDay());
        Date date = cal.getTime();
        
        viewModel.getEntryByDate(date).observe(getViewLifecycleOwner(), entry -> {
            if (entry != null) {
                // Show entry details
                binding.cardSelectedDate.setVisibility(View.VISIBLE);
                binding.textSelectedDate.setText(formatDate(calendarDay));
                binding.textEntryPreview.setText(entry.getContent());
                binding.textEntryPreview.setVisibility(View.VISIBLE);
                binding.textNoEntry.setVisibility(View.GONE);
                binding.buttonEditEntry.setVisibility(View.VISIBLE);
                binding.buttonNewEntry.setText("Edit Entry");
                
                // Set click listener for editing
                binding.buttonEditEntry.setOnClickListener(v -> {
                    Intent intent = new Intent(getContext(), JournalEntryActivity.class);
                    intent.putExtra("entry_id", entry.getId());
                    startActivity(intent);
                });
                
            } else {
                // No entry for this date
                binding.cardSelectedDate.setVisibility(View.VISIBLE);
                binding.textSelectedDate.setText(formatDate(calendarDay));
                binding.textEntryPreview.setVisibility(View.GONE);
                binding.textNoEntry.setVisibility(View.VISIBLE);
                binding.buttonEditEntry.setVisibility(View.GONE);
                binding.buttonNewEntry.setText("New Entry");
            }
        });
    }
    
    private String formatDate(CalendarDay date) {
        Calendar cal = Calendar.getInstance();
        cal.set(date.getYear(), date.getMonth(), date.getDay());
        
        if (CalendarDay.today().equals(date)) {
            return "Today";
        } else if (CalendarDay.today().equals(CalendarDay.from(cal.getTime()).plusDays(1))) {
            return "Yesterday";
        } else {
            return String.format("%s %d, %d", 
                getMonthName(date.getMonth()), 
                date.getDay(), 
                date.getYear());
        }
    }
    
    private String getMonthName(int month) {
        String[] months = {"January", "February", "March", "April", "May", "June",
                          "July", "August", "September", "October", "November", "December"};
        return months[month];
    }
    
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}