package com.soulsync.app.data.model;

public enum Mood {
    HAPPY("happy", "😊", "#8BC34A"),
    SAD("sad", "😢", "#03A9F4"),
    ANGRY("angry", "😠", "#F44336"),
    ANXIOUS("anxious", "😰", "#FF9800"),
    CALM("calm", "😌", "#9C27B0"),
    EXCITED("excited", "🤩", "#FFEB3B"),
    NEUTRAL("neutral", "😐", "#9E9E9E");
    
    private final String value;
    private final String emoji;
    private final String color;
    
    Mood(String value, String emoji, String color) {
        this.value = value;
        this.emoji = emoji;
        this.color = color;
    }
    
    public String getValue() { return value; }
    public String getEmoji() { return emoji; }
    public String getColor() { return color; }
    
    public static Mood fromValue(String value) {
        for (Mood mood : values()) {
            if (mood.value.equals(value)) {
                return mood;
            }
        }
        return NEUTRAL;
    }
    
    public static Mood fromSentiment(float sentimentScore) {
        if (sentimentScore > 0.6) return HAPPY;
        if (sentimentScore > 0.2) return CALM;
        if (sentimentScore > -0.2) return NEUTRAL;
        if (sentimentScore > -0.6) return SAD;
        return ANGRY;
    }
}