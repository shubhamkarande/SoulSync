# SoulSync Setup Guide

## Prerequisites

### Android Development
- Android Studio Arctic Fox or later
- Android SDK API 24+ (Android 7.0)
- Java 8 or later

### Backend Development
- Java 17 or later
- Maven 3.6+
- MongoDB Atlas account (or local MongoDB)

### Optional AI Services
- OpenAI API key for advanced sentiment analysis
- AWS account for Lambda functions

## Quick Start

### 1. Android App Setup

1. **Open Android Studio**
   ```bash
   # Navigate to the android-app directory
   cd android-app
   ```

2. **Open the project in Android Studio**
   - File → Open → Select `android-app` folder
   - Wait for Gradle sync to complete

3. **Configure dependencies**
   - Ensure all dependencies in `app/build.gradle` are resolved
   - Add font files to `res/font/` directory:
     - `lora_regular.ttf`
     - `lora_bold.ttf`

4. **Run the app**
   - Connect an Android device or start an emulator
   - Click Run button or press Ctrl+R

### 2. Backend Setup

1. **Navigate to backend directory**
   ```bash
   cd backend
   ```

2. **Configure MongoDB**
   - Create a MongoDB Atlas cluster or use local MongoDB
   - Update `application.yml` with your MongoDB URI:
   ```yaml
   spring:
     data:
       mongodb:
         uri: mongodb+srv://username:password@cluster.mongodb.net/soulsync
   ```

3. **Configure OpenAI (Optional)**
   - Get an OpenAI API key from https://platform.openai.com/
   - Set environment variable:
   ```bash
   export OPENAI_API_KEY=your_openai_api_key_here
   ```

4. **Run the backend**
   ```bash
   mvn spring-boot:run
   ```
   
   Or using Maven wrapper:
   ```bash
   ./mvnw spring-boot:run
   ```

### 3. Environment Variables

Create a `.env` file in the backend directory:

```env
# Database
MONGODB_URI=mongodb+srv://username:password@cluster.mongodb.net/soulsync

# AI Services
OPENAI_API_KEY=your_openai_api_key_here

# AWS (Optional)
AWS_REGION=us-east-1
AWS_LAMBDA_SENTIMENT_FUNCTION=sentiment-analysis-function

# Security
JWT_ISSUER_URI=https://your-auth-provider.com

# CORS
CORS_ALLOWED_ORIGINS=http://localhost:3000,http://localhost:8081
```

## API Endpoints

### Journal Entries
- `GET /api/journal/entries?userId={userId}` - Get all entries for user
- `GET /api/journal/entries/{id}?userId={userId}` - Get specific entry
- `GET /api/journal/entries/date/{date}?userId={userId}` - Get entry by date
- `POST /api/journal/entries` - Create new entry
- `PUT /api/journal/entries/{id}` - Update entry
- `DELETE /api/journal/entries/{id}?userId={userId}` - Delete entry
- `POST /api/journal/entries/{id}/analyze?userId={userId}` - Analyze entry mood

### Request/Response Examples

**Create Entry:**
```json
POST /api/journal/entries
{
  "userId": "user123",
  "content": "Today was a great day! I felt really productive and happy.",
  "mood": "happy"
}
```

**Response:**
```json
{
  "id": "entry456",
  "userId": "user123",
  "content": "Today was a great day! I felt really productive and happy.",
  "mood": "happy",
  "detectedMood": "happy",
  "sentimentScore": 0.8,
  "wordCount": 12,
  "createdAt": "2024-12-15T10:30:00",
  "updatedAt": "2024-12-15T10:30:00"
}
```

## Database Schema

### MongoDB Collections

**journal_entries:**
```json
{
  "_id": "ObjectId",
  "userId": "string",
  "content": "string",
  "mood": "string",
  "detectedMood": "string",
  "sentimentScore": "float",
  "wordCount": "integer",
  "createdAt": "datetime",
  "updatedAt": "datetime"
}
```

**affirmations:**
```json
{
  "_id": "ObjectId",
  "userId": "string",
  "journalEntryId": "string",
  "content": "string",
  "category": "string",
  "isFavorite": "boolean",
  "createdAt": "datetime"
}
```

## Troubleshooting

### Common Issues

1. **Gradle Sync Failed**
   - Check internet connection
   - Update Android Studio
   - Clean and rebuild project

2. **MongoDB Connection Error**
   - Verify MongoDB URI
   - Check network connectivity
   - Ensure database user has proper permissions

3. **OpenAI API Errors**
   - Verify API key is correct
   - Check API usage limits
   - Ensure sufficient credits

4. **App Crashes on Startup**
   - Check logcat for error messages
   - Verify all dependencies are properly configured
   - Clear app data and restart

### Development Tips

1. **Testing without Backend**
   - The app works offline-first with Room database
   - You can test core functionality without backend

2. **Mock Data**
   - Use the sample data in the app for testing
   - Backend includes fallback sentiment analysis

3. **Debugging**
   - Enable debug logging in `application.yml`
   - Use Android Studio debugger for app issues
   - Check backend logs for API issues

## Next Steps

1. **Add Authentication**
   - Implement user registration/login
   - Add JWT token handling

2. **Enhance AI Features**
   - Implement affirmation generation
   - Add mood pattern analysis

3. **Improve UI/UX**
   - Add animations and transitions
   - Implement dark theme
   - Add accessibility features

4. **Deploy to Production**
   - Set up CI/CD pipeline
   - Deploy backend to cloud platform
   - Publish app to Google Play Store