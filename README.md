# 🧠 Quiz Generation Microservice — Spring Boot + Gemini AI

Welcome to the **AI Quiz Generation Microservice**, a backend-powered intelligent quiz creation system. Leveraging Google's Gemini API via LangChain in Python, it dynamically generates MCQs based on grade, subject, and topic inputs. Built with **Java Spring Boot**, **PostgreSQL**, **Docker**, and a modular clean architecture — it is production-ready and highly scalable.

---

## 🚀 Key Features

* 🧠 AI-generated MCQs using LangChain + Google Gemini API
* ✅ Clean REST APIs built with Spring Boot (Java 21)
* 🗃️ PostgreSQL backend integration via Spring Data JPA
* 🐍 Python script integration via `ProcessBuilder`
* 📦 Dockerized with multi-stage builds
* 🧾 Swagger UI included for API testing/documentation
* 🔐 Environment variable-based config for secrets
* 🧩 Modular layered architecture (Controller-Service-Repository)

---

## 🧱 Tech Stack

| Layer         | Tech                                     |
| ------------- | ---------------------------------------- |
| Backend       | Spring Boot, Java 21, Maven              |
| AI Generation | Python 3, LangChain, Gemini API          |
| Database      | PostgreSQL                               |
| Container     | Docker (multi-stage)                     |
| Documentation | Swagger UI (OpenAPI)                     |
| Deployment    | Railway, Render, or Localhost via Docker |

---

## 📁 Project Structure

```
quiz-service/
├── src/
│   ├── main/java/com/thinkforge/quiz_service/
│   │   ├── controller/      # REST controllers
│   │   ├── service/         # Business logic
│   │   ├── repository/      # Spring Data JPA
│   │   ├── entity/          # JPA entity classes
│   │   ├── dto/             # Request/Response DTOs
│   │   ├── config/          # Configuration beans
│   │   ├── exception/       # Custom exception handling
│   │   └── mapper/          # DTO-Entity mappers
│   └── resources/
│       └── application.properties
├── generate.py              # Python AI Quiz generator
├── Dockerfile               # Docker multi-stage build
├── requirements.txt         # Python dependencies
└── README.md
```

---

## 🔐 Environment Variables

These must be provided via `.env` or container runtime (like Railway, Docker):

```env
DATABASE_URL=jdbc:postgresql://<host>:5432/<db>
DATABASE_USERNAME=<db_user>
DATABASE_PASSWORD=<db_pass>
GEMINI_API_KEY=<your_gemini_api_key>
```

> `.env` is safely ignored by Git.

---

## 🐳 Docker Build & Run

### ⛏️ Build

```bash
docker build -t quiz-service .
```

### ▶️ Run (Linux/Mac)

```bash
docker run -p 8080:8080 \
-e DATABASE_URL="<db_url>" \
-e DATABASE_USERNAME="<username>" \
-e DATABASE_PASSWORD="<password>" \
-e GEMINI_API_KEY="<api_key>" \
quiz-service
```

### ▶️ Run (PowerShell)

```powershell
$env:DATABASE_URL="<db_url>"
$env:DATABASE_USERNAME="<username>"
$env:DATABASE_PASSWORD="<password>"
$env:GEMINI_API_KEY="<api_key>"

docker run -p 8080:8080 `
-e DATABASE_URL=$env:DATABASE_URL `
-e DATABASE_USERNAME=$env:DATABASE_USERNAME `
-e DATABASE_PASSWORD=$env:DATABASE_PASSWORD `
-e GEMINI_API_KEY=$env:GEMINI_API_KEY `
quiz-service
```

---

## 🔗 REST API Endpoints

| Method | Endpoint                                       | Description                            |
| ------ | ---------------------------------------------- | -------------------------------------- |
| GET    | `/api/v1/quiz`                                 | Get all quizzes                        |
| GET    | `/api/v1/quiz/{quizId}`                        | Get quiz metadata by ID                |
| GET    | `/api/v1/quiz/teacher/{teacherId}`             | Get quizzes created by a teacher       |
| POST   | `/api/v1/quiz/generate`                        | Generate quiz using Gemini AI          |
| PUT    | `/api/v1/quiz/{quizId}`                        | Update quiz metadata                   |
| DELETE | `/api/v1/quiz/{quizId}`                        | Delete a quiz                          |
| POST   | `/api/v1/quiz/{quizId}/submit`                 | Submit quiz answers                    |
| GET    | `/api/v1/quiz/{quizId}/submissions`            | Get all submissions for a quiz         |
| GET    | `/api/v1/quiz/{quizId}/submission/{studentId}` | Get submission of a student            |
| GET    | `/api/v1/quiz/{quizId}/analysis`               | Analyze all submissions (aggregated)   |
| GET    | `/api/v1/quiz/{quizId}/status`                 | View score distribution/status summary |

---

## 📥 Sample Request

```http
POST /api/v1/quiz/generate
Content-Type: application/json

{
  "teacherId": "UUID",
  "grade": 10,
  "subject": "Geography",
  "topic": "Glacier",
  "numOfQuestions": 5,
  "deadline": "2025-05-18T23:59:59.000Z"
}
```

---

## ⚙️ Quiz Generation Flow

1. Frontend submits metadata to `/generate`.
2. Java service runs `generate.py` using `ProcessBuilder`.
3. Python script uses Gemini API to generate MCQs.
4. Response is validated and saved via Spring Boot backend.

---

## 🔄 Planned Future Updates

* [ ] 🗃️ Add MongoDB-based logging for quiz activity and errors
* [ ] 🔐 JWT-based authentication with teacher/student roles
* [ ] 🧾 Swagger UI (already integrated)
* [ ] 🧑‍💼 Admin dashboard + frontend portal
* [ ] ⚡ Caching for repeated quiz generation (Redis or in-memory)

---

## 🧪 Testing & Documentation

* ✅ DTO-based validation with appropriate error handling
* ✅ Swagger UI enabled at `/swagger-ui/index.html`
* 🔬 Unit testing (JUnit + MockMvc) coming soon
* 🧪 Optional Postman collection for manual testing

---

## 🧰 Contribution Guidelines

We welcome contributions! You can help by:

* 🐛 Reporting bugs or proposing features via GitHub Issues
* ✅ Writing unit tests or integration test cases
* 🧠 Improving documentation or adding diagrams
* 📤 Creating pull requests with clear change logs

Please ensure all code is clean and adheres to Spring Boot and Java standards.

---

## 🙏 Credits

* Developed with ❤️ by [Arghya Banerjee](https://github.com/Arghya-Banerjee)
* Powered by [Google Gemini API](https://ai.google.dev/)
* Inspired by real classroom needs for AI-based auto-assessment tools

---

## 📄 License

This project is licensed under the MIT License. See the `LICENSE` file for details.
