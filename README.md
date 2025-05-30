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
