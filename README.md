Project Description
ProActivibe is a mobile application designed to help users manage their daily tasks, set reminders, and track their achievements. The app allows users to:

Create tasks with titles, descriptions, and deadlines

Receive notifications for task reminders

View and manage pending, completed, or canceled tasks

Track achievements by completing tasks

Maintain a user profile with basic statistics

Technologies Used
Programming Language: Java

Platform: Android

Database: Firebase Realtime Database

Authentication: Firebase Authentication

Notifications: AlarmManager and BroadcastReceiver

Project Structure
The project is organized into the following main activities:

Authentication:

MainActivity: Home screen with login and registration options

Inicio_De_Sesion: Login form

Registro: New user registration form

Pantalla_De_Carga: Initial loading screen that redirects based on authentication status

Core Features:

MenuPrincipal: Main menu with access to all functionalities

Agregar_Tarea: Form to create new tasks with reminders

Listar_Tareas: View of pending tasks

Detalles_Actividad: Details of a specific task with options to mark as completed or canceled

Logros: View of unlocked achievements

Services:

Notificaciones: Handles push notifications for task reminders

Project Setup
Prerequisites
Android Studio (recommended version: Arctic Fox or later)

Android SDK (API level 23 or higher)

Firebase account with a configured project

Setup Steps
Clone the repository

Open the project in Android Studio

Configure Firebase:

Add the google-services.json file to the app/ directory

Enable Authentication (Email/Password) in Firebase Console

Enable Realtime Database in Firebase Console with appropriate security rules

Sync the project with Gradle

Key Features
Task Management
Create tasks with specific dates and times

Automatic notifications when deadlines approach

Change task status (Pending, Completed, Canceled)

Achievement System
Achievement for first task created

Achievement for first task completed

View unlocked achievements

Authentication and Profile
Register and log in with email and password

View basic user information

Secure logout

Screenshots
(Include screenshots of the app's main screens)

License
This project is licensed under the MIT License. See the LICENSE file for details.

Contributions
Contributions are welcome. Please open an issue or submit a pull request for any improvements or fixes.

Contact
For any questions or suggestions, please contact the development team.

Hazlo para github
ProActivibe - Task Management App 📋✅
Android
Firebase
Java

📌 Table of Contents
Features

Screenshots

Installation

Tech Stack

Project Structure

Contributing

License

✨ Features
User Authentication 🔐

Secure login/register with Firebase Auth

User profile management

Task Management ✅

Create tasks with deadlines

Set reminders with notifications

Mark tasks as completed/canceled

Achievements System 🏆

Unlock badges for completing tasks

Track your productivity progress

Real-time Sync ⚡

All data syncs instantly via Firebase

📸 Screenshots
Login Screen	Task List	Add Task
<img src="screenshots/login.jpg" width="200">	<img src="screenshots/task_list.jpg" width="200">	<img src="screenshots/add_task.jpg" width="200">
📥 Installation
Clone the repository:

bash
git clone https://github.com/yourusername/ProActivibe.git
Open in Android Studio

Add your google-services.json file

Run on emulator or physical device

🛠 Tech Stack
Frontend: Java, XML

Backend: Firebase

Authentication

Realtime Database

Notifications: AlarmManager, BroadcastReceiver

📂 Project Structure
app/
├── src/
│   ├── main/
│   │   ├── java/com/josemaria/proactivibe/
│   │   │   ├── activities/          # All app screens
│   │   │   ├── services/            # Notification service
│   │   │   └── utils/               # Helper classes
│   │   └── res/                     # Layouts, strings, drawables
├── build.gradle
└── google-services.json
🤝 Contributing
Contributions are welcome! Please follow these steps:

Fork the project

Create your feature branch (git checkout -b feature/AmazingFeature)

Commit your changes (git commit -m 'Add some amazing feature')

Push to the branch (git push origin feature/AmazingFeature)

Open a Pull Request
