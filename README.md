# SoulSync - Reflect. Heal. Evolve.

A comprehensive mental health journal app built with Java for Android and Spring Boot backend.

## Project Structure

```
SoulSync/
├── android-app/          # Native Android app (Java)
├── backend/              # Spring Boot REST API
├── docs/                 # Documentation and design specs
└── README.md
```

## Features

- **Daily Journaling**: Rich-text entries with mood tracking
- **AI Mood Detection**: Sentiment analysis from journal entries
- **Personalized Affirmations**: AI-generated motivational content
- **Calendar View**: Monthly overview with mood indicators
- **Offline-First**: Room DB with cloud sync to MongoDB
- **Secure Authentication**: Optional user accounts with encryption

## Tech Stack

### Frontend (Android)
- Java with Android Studio
- XML Layouts
- Room Database (offline storage)
- Material Design components

### Backend
- Spring Boot (Java)
- MongoDB Atlas
- AWS Lambda (AI processing)
- RESTful API design

### AI Integration
- Sentiment analysis via OpenAI/AWS
- Personalized affirmation generation

## Getting Started

1. **Android App**: Open `android-app/` in Android Studio
2. **Backend**: Run Spring Boot app from `backend/`
3. **Database**: Configure MongoDB Atlas connection
4. **AI Services**: Set up OpenAI API keys

## Design Guidelines

- **Typography**: Lora/Noto Serif for calming effect
- **Colors**: Soft palette with mood-based indicators
- **Theme**: Minimal journal-style with subtle animations
- **UX**: Offline-first with seamless sync