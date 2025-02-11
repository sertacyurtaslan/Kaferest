# Kaferest - Modern Coffee Shop Management Platform

<div align="center">
  <img src="app/src/main/res/mipmap-xxxhdpi/ic_launcher.png" alt="Kaferest Logo" width="120"/>
</div>

## 📱 About

Kaferest is a modern Android application that bridges the gap between coffee shop owners and their customers. Built with Jetpack Compose and following Material Design 3 principles, it provides a seamless experience for both shop management and customer interaction.

## ✨ Features

### For Customers
- **Shop Discovery**: Browse and discover coffee shops with detailed information and ratings
- **Featured Cafes**: Explore trending and popular cafes in your area
- **Menu Browsing**: View comprehensive menus with categories and prices
- **Search Functionality**: Find specific shops or items easily
- **Interactive UI**: Modern, responsive interface with smooth animations
- **Multilingual Support**: Available in English and Turkish

### For Shop Owners
- **Shop Management**: Create and manage shop profiles
- **Menu Management**: Add, edit, and organize menu items by categories
- **Photo Management**: Upload and manage shop photos
- **Real-time Updates**: Changes reflect immediately to customers
- **Analytics**: Track shop performance and customer engagement

## 🛠 Technology Stack

- **Language**: Kotlin
- **UI Framework**: Jetpack Compose
- **Architecture**: MVVM (Model-View-ViewModel)
- **Dependency Injection**: Hilt
- **Backend**: Firebase (Firestore, Authentication, Storage)
- **Image Loading**: Coil
- **Async Operations**: Kotlin Coroutines & Flow
- **Material Design**: Material Design 3 Components

## 🏗 Architecture

The application follows Clean Architecture principles and is organized into the following layers:

```
app/
├── data/           # Data layer with repositories and data sources
├── domain/         # Business logic and models
├── presentation/   # UI layer with screens and viewmodels
│   ├── admin/      # Admin/Shop owner features
│   ├── customer/   # Customer features
│   └── shared/     # Shared UI components
└── util/           # Utility classes and extensions
```

## 🔐 Security

- Secure authentication using Firebase Auth
- Role-based access control
- Encrypted data storage
- Secure API communication

## 🎨 UI/UX Features

- Material Design 3 components
- Dynamic theming
- Dark mode support
- Responsive layouts
- Smooth animations
- Intuitive navigation

## 📱 Screenshots

[Add screenshots here]

## 🚀 Getting Started

### Prerequisites
- Android Studio Arctic Fox or later
- JDK 11 or later
- Android SDK 21 or later

### Installation
1. Clone the repository
   ```bash
   git clone https://github.com/yourusername/Kaferest.git
   ```
2. Open the project in Android Studio
3. Sync project with Gradle files
4. Add your `google-services.json` file for Firebase
5. Build and run the project

## 🛠 Configuration

### Firebase Setup
1. Create a new Firebase project
2. Add your Android app to Firebase project
3. Download `google-services.json`
4. Enable Authentication and Firestore

### Build Configuration
```gradle
android {
    compileSdk 34
    defaultConfig {
        minSdk 21
        targetSdk 34
    }
}
```

## 🤝 Contributing

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details

## 👥 Authors

- Your Name - [GitHub Profile](https://github.com/yourusername)

## 🙏 Acknowledgments

- Material Design for design inspiration
- Firebase for backend services
- Jetpack Compose for modern UI toolkit
- All contributors who helped shape the project

## 📞 Contact

Your Name - [@yourtwitter](https://twitter.com/yourtwitter) - email@example.com

Project Link: [https://github.com/yourusername/Kaferest](https://github.com/yourusername/Kaferest) 