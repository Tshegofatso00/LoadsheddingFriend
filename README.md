# LoadsheddingFriend

LoadsheddingFriend is an Android app prototype demonstrating user authentication, community reporting, and offline features related to load shedding schedules. This project was built as part of an academic submission.

---

## **Features Implemented**

### ✅ Authentication
- Firebase Email/Password registration and login
- Users stored in Firebase Authentication → `/users`

### ✅ Settings
- Enable/disable notifications
- Language toggle (English / isiZulu)
- Clear local data

### ✅ Community Report
- Users can submit reports about outages
- Reports stored in Firebase Realtime Database → `/reports`

### ✅ Charts / Statistics
- MPAndroidChart bar chart showing fake outage hours

### ✅ Offline Cache
- Room database stores schedules locally for offline access

---

## **Technical Details**

- **Language:** Kotlin
- **Framework:** Android Studio, Jetpack Compose
- **Firebase Services:** Authentication, Realtime Database, Messaging
- **Chart Library:** MPAndroidChart
- **Local Database:** Room

---

## **Screenshots**

> 

---

## **How to Run the App**

1. Clone the repository:
```bash
git clone <your-repo-link>
