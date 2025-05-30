# ProActivibe - Task Management App 📋✅

**ProActivibe** es una aplicación móvil para Android diseñada para ayudar a los usuarios a gestionar sus tareas diarias, establecer recordatorios y hacer seguimiento de sus logros personales.

---

## 📌 Tabla de Contenidos

- [✨ Features](#-features)
- [📥 Installation](#-installation)
- [🛠 Tech Stack](#-tech-stack)
- [📂 Project Structure](#-project-structure)
- [🤝 Contributing](#-contributing)

---

## ✨ Features

### 🔐 User Authentication
- Registro e inicio de sesión seguros con Firebase Authentication.
- Gestión básica del perfil de usuario.
- Cierre de sesión seguro.

### ✅ Task Management
- Crear tareas con títulos, descripciones, fechas y horas límite.
- Notificaciones automáticas cuando se aproxima una fecha límite.
- Cambiar el estado de la tarea (Pendiente, Completada, Cancelada).

### 🏆 Achievements System
- Logro por crear la primera tarea.
- Logro por completar la primera tarea.
- Visualización de los logros desbloqueados.

### ⚡ Real-time Sync
- Sincronización en tiempo real con Firebase Realtime Database.

---

## 📥 Installation

Sigue estos pasos para ejecutar el proyecto en tu entorno local:

```bash````
git clone https://github.com/yourusername/ProActivibe.git
Abre el proyecto en Android Studio.

Agrega tu archivo google-services.json en el directorio app/.

Asegúrate de tener configurado un proyecto en Firebase:

Habilita Authentication con Email/Password.

Habilita Realtime Database con las reglas de seguridad adecuadas.

Sincroniza el proyecto con Gradle.

Ejecuta la app en un emulador o dispositivo físico.

🛠 Tech Stack
Frontend:

Java

XML

Backend:

Firebase Authentication

Firebase Realtime Database

Notificaciones:

AlarmManager

BroadcastReceiver

📂 Project Structure
bash
Copiar
Editar
app/
├── src/
│   ├── main/
│   │   ├── java/com/josemaria/proactivibe/
│   │   │   ├── activities/        # Pantallas principales de la app
│   │   │   ├── services/          # Servicio de notificaciones
│   │   │   └── utils/             # Clases auxiliares
│   │   └── res/                   # Layouts, strings, drawables
├── build.gradle
└── google-services.json
🤝 Contributing
¡Las contribuciones son bienvenidas! Sigue estos pasos:

Haz un fork del proyecto.

Crea una rama para tu nueva funcionalidad (git checkout -b feature/NuevaFeature).

Realiza tus cambios y haz commit (git commit -m 'Agrega una nueva funcionalidad').

Haz push a tu rama (git push origin feature/NuevaFeature).

Abre un Pull Request.
