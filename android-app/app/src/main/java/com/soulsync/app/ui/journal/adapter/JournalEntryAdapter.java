package com.soulsync.app.ui.journal.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.soulsync.app.R;
import com.soulsync.app.data.model.JournalEntry;
import com.soulsync.app.data.model.Mood;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class JournalEntryAdapter extends ListAdapter<JournalEntry, JournalEntryAdapter.ViewHolder> {
    
    private OnEntryClickListener listener;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());
    private SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a", Locale.getDefault());
    
    public interface OnEntryClickListener {
        void onEntryClick(JournalEntry entry);
    }
    
    public JournalEntryAdapter(OnEntryClickListener listener) {
        super(DIFF_CALLBACK);
        this.listener = listener;
    }
    
    private static final DiffUtil.ItemCallback<JournalEntry> DIFF_CALLBACK = 
        new DiffUtil.ItemCallback<JournalEntry>() {
            @Override
            public boolean areItemsTheSame(@NonNull JournalEntry oldItem, @NonNull JournalEntry newItem) {
                return oldItem.getId().equals(newItem.getId());
            }
            
            @Override
            public boolean areContentsTheSame(@NonNull JournalEntry oldItem, @NonNull JournalEntry newItem) {
                return oldItem.getContent().equals(newItem.getContent()) &&
                       oldItem.getMood().equals(newItem.getMood()) &&
                       oldItem.getUpdatedAt().equals(newItem.getUpdatedAt());
            }
        };
    
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.item_journal_entry, parent, false);
        return new ViewHolder(view);
    }
    
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        JournalEntry entry = getItem(position);
        holder.bind(entry);
    }
    
    class ViewHolder extends RecyclerView.ViewHolder {
        private MaterialCardView cardView;
        private TextView textDate;
        private TextView textTime;
        private TextView textContent;
        private TextView textMood;
        private TextView textWordCount;
        private View moodIndicator;
        
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card_entry);
            textDate = itemView.findViewById(R.id.text_date);
            textTime = itemView.findViewById(R.id.text_time);
            textContent = itemView.findViewById(R.id.text_content);
            textMood = itemView.findViewById(R.id.text_mood);
            textWordCount = itemView.findViewById(R.id.text_word_count);
            moodIndicator = itemView.findViewById(R.id.mood_indicator);
            
            cardView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION && listener != null) {
                    listener.onEntryClick(getItem(position));
                }
            });
        }
        
        void bind(JournalEntry entry) {
            textDate.setText(dateFormat.format(entry.getCreatedAt()));
            textTime.setText(timeFormat.format(entry.getCreatedAt()));
            
            // Truncate content for preview
            String content = entry.getContent();
            if (content.length() > 150) {
                content = content.substring(0, 150) + "...";
            }
            textContent.setText(content);
            
            textWordCount.setText(entry.getWordCount() + " words");
            
            // Set mood
            if (entry.getMood() != null) {
                Mood mood = Mood.fromValue(entry.getMood());
                textMood.setText(mood.getEmoji() + " " + mood.getValue());
                textMood.setVisibility(View.VISIBLE);
                
                // Set mood indicator color
                moodIndicator.setBackgroundColor(Color.parseColor(mood.getColor()));
                moodIndicator.setVisibility(View.VISIBLE);
            } else {
                textMood.setVisibility(View.GONE);
                moodIndicator.setVisibility(View.GONE);
            }
        }
    }
}