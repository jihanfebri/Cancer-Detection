# 🏥 Asclepius

![Android](https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white)
![Kotlin](https://img.shields.io/badge/Kotlin-0095D5?&style=for-the-badge&logo=kotlin&logoColor=white)
![Android Studio](https://img.shields.io/badge/Android_Studio-3DDC84?style=for-the-badge&logo=android-studio&logoColor=white)

## 📱 Overview

Asclepius is a modern Android healthcare assistant application built with Kotlin that helps users manage their health records and access medical information. Named after the ancient Greek god of medicine, this app combines cutting-edge technology with healthcare needs.

The application leverages machine learning capabilities to provide intelligent healthcare assistance:
- **TensorFlow Lite Integration**: Utilizes on-device ML models for efficient processing
- **Image Classification**: Advanced medical image analysis for preliminary health assessments
- **Real-time Processing**: Fast and secure on-device machine learning inference
- **Custom ML Models**: Specifically trained for healthcare-related image analysis

## ✨ Features

### 🔍 Image Analysis & Classification
- **Image Upload**: Easy image selection from gallery with built-in cropping functionality
- **Real-time Analysis**: Fast on-device processing using TensorFlow Lite
- **Detailed Results**: Clear visualization of classification results with confidence scores
- **Progress Tracking**: Visual progress bars showing classification confidence levels

### 📊 Health Records Management
- **History Tracking**: Complete history of all analyzed images and results
- **Record Storage**: Local database storage using Room for all health records
- **Easy Access**: Quick access to previous analyses and results
- **Record Management**: Ability to view and delete previous records

### 📰 Medical News Integration
- **Latest Health Articles**: Integration with health news API
- **Relevant Content**: News articles related to health and medical topics
- **Article Preview**: Clean article previews with titles and descriptions
- **External Links**: Direct access to full article content

### 💫 User Experience
- **Modern UI**: Material Design implementation with clean aesthetics
- **Intuitive Navigation**: Easy navigation between features
- **Image Cropping**: Built-in image cropping functionality using UCrop
- **Dark Mode Support**: Customizable app appearance settings

### 🔒 Privacy & Security
- **On-device Processing**: All ML processing done locally on the device
- **Secure Storage**: Private and secure local storage of health records
- **No External Upload**: Images are processed locally without external uploads
- **Data Privacy**: Complete control over personal health data

## 🛠 Tech Stack

- **Language**: Kotlin
- **Architecture**: MVVM (Model-View-ViewModel)
- **Dependencies**:
  - AndroidX Components
  - Kotlin Coroutines
  - Room Database
  - Retrofit for API calls
  - Material Design Components
  - ViewModel & LiveData
  - Dependency Injection

## 🚀 Getting Started

### Prerequisites

- Android Studio Arctic Fox or later
- Android SDK 24 or higher
- Kotlin 1.8+

### Installation

1. Clone the repository
   ```bash
   git clone https://github.com/jihanfw/asclepius.git
   ```

2. Open the project in Android Studio

3. Create a `local.properties` file in the root directory and add your API credentials:
   ```properties
   API_URL="your_api_url"
   API_KEY="your_api_key"
   ```

4. Sync the project with Gradle files

5. Run the app on an emulator or physical device

## 🏗 Project Structure

```
app/
├── data/
│   ├── local/          # Room Database
│   ├── remote/         # API Services
│   └── repository/     # Data Repositories
├── di/                 # Dependency Injection
├── ui/
│   ├── main/          # Main Screen
│   ├── record/        # Health Records
│   └── result/        # Analysis Results
├── util/              # Utility Classes
└── view/              # UI Components
```

## 🔒 Security

- All sensitive data is stored securely using Room Database
- API keys are stored in `local.properties` and not tracked in version control
- Network calls are made over HTTPS

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📝 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 📧 Contact

Jihan Febriharvianti Wirawan

[![LinkedIn](https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white)](https://linkedin.com/in/jihanfebriharvianti)
[![GitHub](https://img.shields.io/badge/GitHub-100000?style=for-the-badge&logo=github&logoColor=white)](https://github.com/jihanfw)

Project Link: [https://github.com/jihanfw/asclepius](https://github.com/jihanfw/asclepius)

---

⭐️ If you found this project helpful, please give it a star!
