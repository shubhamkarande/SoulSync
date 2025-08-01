package com.soulsync.app.ui.calendar;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.soulsync.app.data.model.Mood;

public class MoodDecorator implements DayViewDecorator {
    
    private final CalendarDay date;
    private final Drawable drawable;
    
    public MoodDecorator(CalendarDay date, String moodValue) {
        this.date = date;
        Mood mood = Mood.fromValue(moodValue);
        this.drawable = new ColorDrawable(Color.parseColor(mood.getColor()));
    }
    
    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return date.equals(day);
    }
    
    @Override
    public void decorate(DayViewFacade view) {
        view.setBackgroundDrawable(drawable);
    }
}