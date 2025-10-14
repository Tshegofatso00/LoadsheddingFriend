# LoadsheddingFriend

LoadsheddingFriend is an Android app prototype designed to help users track load shedding schedules, submit community reports, and manage personal settings. This project was built for academic submission and demonstrates Firebase integration, local caching, and a simple user interface.

---

## **Features Implemented**

### ✅ Authentication
- Firebase Email/Password registration and login
- Users are stored in Firebase Authentication
- Realtime Database path: `/users/{uid}` stores user info (email, full name)

### ✅ Settings
- Enable/disable notifications (simulated with Toast messages)
- Language toggle (English / isiZulu) using SharedPreferences
- Clear local data (clears preferences)

### ✅ Community Report
- Users can submit reports about outages
- Reports are stored in Firebase Realtime Database → `/reports/{report_id}`
- Each report stores:
    - `title` (text)
    - `description` (text)
    - `timestamp` (server time)

### ✅ Charts / Statistics
- MPAndroidChart bar chart displays fake outage hours for demonstration

### ✅ Offline Cache
- Room database stores schedules locally for offline access

---

## **Technical Details**

- **Language:** Kotlin
- **Framework:** Android Studio, Jetpack Compose
- **Firebase Services:** Authentication, Realtime Database, Cloud Messaging
- **Chart Library:** MPAndroidChart
- **Local Database:** Room
- **Minimum SDK:** 24
- **Target SDK:** 36

---
### 🔥 Firebase Integration
This project is connected to Firebase for Authentication and Realtime Database.
The `google-services.json` file is included in `/app` to allow the marker to verify configuration.


## **Screenshots**
> Add screenshots of the app running here (Login, Settings, Community Report, Chart screen)

---

## **How to Run the App**

1. Clone the repository:
```bash
git clone <your-repo-link>
