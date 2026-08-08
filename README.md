# TaskFlow — Secure Task Management Backend 🚀

TaskFlow is a **Kotlin + Spring Boot** backend for a task-management service. It now includes user registration/login, BCrypt password hashing, JWT authentication, PostgreSQL persistence, authenticated task CRUD, request validation, and consistent API error responses.

> **Status:** Backend MVP in active development. WebSocket events, automated integration coverage, Android integration, and AI-assisted features remain on the roadmap.

## 🧱 Architecture

```text
Client
  │
  ├── POST /api/auth/register
  ├── POST /api/auth/login
  │          │
  │          ▼
  │     BCrypt + JWT
  │          │
  │          ▼
  └── Bearer Token ──► Spring Security ──► Task API
                                           │
                                           ▼
                                      Spring Data JPA
                                           │
                                           ▼
                                       PostgreSQL
```

## ✨ Implemented Features

- User registration with password hashing
- JWT-based stateless authentication
- Protected task endpoints
- User-owned task isolation
- Create, read, update, and delete tasks
- Bean Validation for request payloads
- Consistent 400/404 API error responses
- Environment-based database and JWT configuration
- PostgreSQL persistence through Spring Data JPA

## 🛠️ Tech Stack

- **Kotlin 1.9.20**
- **Java 17**
- **Spring Boot 3.2**
- Spring Web
- Spring Security
- Spring Data JPA
- Spring Validation
- PostgreSQL
- JJWT
- Kotlin Coroutines

## 🔌 API Overview

### Authentication

```http
POST /api/auth/register
POST /api/auth/login
```

Example registration body:

```json
{
  "email": "user@example.com",
  "password": "strong-password"
}
```

Both authentication endpoints return a JWT token.

### Tasks

All task endpoints require:

```http
Authorization: Bearer <token>
```

Available endpoints:

```text
GET    /api/tasks
GET    /api/tasks/{id}
POST   /api/tasks
PUT    /api/tasks/{id}
DELETE /api/tasks/{id}
```

Task records are associated with the authenticated user, so one user cannot retrieve or modify another user's tasks through the task API.

## 📁 Project Structure

```text
TaskFlow/
├── backend/
│   ├── build.gradle.kts
│   └── src/main/
│       ├── kotlin/com/taskflow/
│       │   ├── auth/
│       │   ├── common/
│       │   ├── security/
│       │   ├── task/
│       │   └── user/
│       └── resources/
│           └── application.yml
├── android-app/
├── .gitignore
├── LICENSE
└── README.md
```

## ▶️ Run Locally

### Prerequisites

- JDK 17+
- PostgreSQL
- Gradle

Create a PostgreSQL database named `taskflow`, then configure environment variables if your local credentials differ from the defaults:

```bash
DB_URL=jdbc:postgresql://localhost:5432/taskflow
DB_USERNAME=postgres
DB_PASSWORD=your-password
JWT_SECRET=your-long-random-secret
JWT_EXPIRATION_MS=86400000
```

From `backend`:

```bash
./gradlew bootRun
```

Windows:

```powershell
.\gradlew.bat bootRun
```

The API starts on port `8080` by default.

## 🔐 Security Notes

- Passwords are stored as BCrypt hashes, never plaintext.
- JWT secrets and database credentials are read from environment variables.
- Task endpoints are stateless and protected by Spring Security.
- Do not commit real credentials or production JWT secrets.

## 📌 Roadmap

- [x] User registration/login
- [x] JWT authentication
- [x] Task CRUD
- [x] User-specific task authorization
- [x] Request validation and API exception handling
- [ ] Database migrations with Flyway
- [ ] WebSocket task events
- [ ] Unit and integration test coverage
- [ ] Dockerized backend deployment
- [ ] Android client integration
- [ ] AI-assisted task functionality
- [ ] CI/CD pipeline

## Author

**Rudra Pratap Singh**

GitHub: [@deadlyrps2802](https://github.com/deadlyrps2802)
