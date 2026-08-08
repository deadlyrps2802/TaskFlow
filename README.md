# TaskFlow — Task Management Backend 🚀

TaskFlow is a **Kotlin + Spring Boot** backend project for building a secure task-management service. The repository currently focuses on the backend foundation, including the application setup, persistence layer dependencies, authentication/security dependencies, validation, and WebSocket support.

> **Status:** Backend foundation / work in progress. Some features described in the original README are planned rather than fully implemented in the current repository.

## 🧱 Current Architecture

```text
TaskFlow/
├── backend/
│   ├── build.gradle.kts
│   └── src/
│       └── main/
│           ├── kotlin/
│           │   └── com/taskflow/
│           │       └── TaskFlowApplication.kt
│           └── resources/
├── android-app/
├── .gitignore
├── LICENSE
└── README.md
```

## 🛠️ Backend Stack

- **Kotlin 1.9.x**
- **Java 17**
- **Spring Boot 3.2**
- Spring Web
- Spring Data JPA
- Spring Security
- Spring WebSocket
- Spring Validation
- PostgreSQL
- Jackson Kotlin
- Kotlin Coroutines
- JJWT for JWT-based authentication

The dependency configuration is defined in `backend/build.gradle.kts`.

## 🎯 Planned / Expandable Features

The project is structured to support the following features as development continues:

- User authentication and authorization
- CRUD operations for tasks
- PostgreSQL persistence with JPA
- JWT-based security
- Real-time task updates using WebSockets
- Task validation and API-level error handling
- Android client integration
- AI-assisted task recommendations

These should be considered **planned capabilities unless the corresponding implementation is present in the repository**.

## ▶️ Running the Backend

### Prerequisites

- JDK 17+
- PostgreSQL
- Gradle wrapper / Gradle

### Start the application

From the `backend` directory:

```bash
./gradlew bootRun
```

On Windows:

```powershell
.\gradlew.bat bootRun
```

Before running the application, configure the required PostgreSQL connection and application security settings in the Spring configuration.

## 🧪 Development Goals

The project is intended as a backend engineering project demonstrating:

- REST API development with Spring Boot
- Kotlin backend development
- Database-backed application design
- Authentication and authorization concepts
- Real-time communication with WebSockets
- Production-oriented project structure

## 📌 Roadmap

- [ ] Implement task CRUD APIs
- [ ] Add user/account domain models
- [ ] Add JWT authentication flow
- [ ] Add PostgreSQL configuration and migrations
- [ ] Add WebSocket task events
- [ ] Add automated unit/integration tests
- [ ] Connect the Android client
- [ ] Add AI-assisted task functionality
- [ ] Add CI/CD and deployment documentation

## Author

**Rudra Pratap Singh**

GitHub: [@deadlyrps2802](https://github.com/deadlyrps2802)
