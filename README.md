# 🧠 Quiz Generation Service using Spring Boot + Gemini API

Welcome to the **AI Quiz Generation Microservice** — an AI-powered quiz creation platform that dynamically generates multiple-choice questions (MCQs) using Google's Gemini (via LangChain), driven by user inputs like grade, subject, and topic. Built with **Spring Boot**, **PostgreSQL**, and a powerful **Python backend**, the service is containerized with Docker and ready for production deployment.

---

## 🚀 Features

- ✨ AI-generated MCQs using Google Gemini (via LangChain)
- ✅ Clean REST API with Spring Boot 3.4+
- 🐘 PostgreSQL integration via Supabase
- 🐳 Fully Dockerized build and runtime
- 🔐 Environment-variable based secrets management (`.env` + Railway + Docker)
- 📦 Clean Maven project structure with Lombok, validation, and test support
- 🌐 Deployed & tested on [Railway.com](https://railway.com)

---

## 🧱 Tech Stack

| Layer        | Technology                            |
|--------------|----------------------------------------|
| Backend      | Java 21, Spring Boot, Spring Data JPA |
| AI/ML Logic  | Python 3, LangChain, Gemini API       |
| Database     | PostgreSQL (hosted on Supabase)       |
| Build Tool   | Maven                                 |
| Dependency   | Lombok, Validation, LangChain         |
| Container    | Docker (multi-stage build)            |
| Deployment   | Railway.com                            |

---

## 📁 Project Structure

```
quiz-service/
├── src/
│   ├── main/
│   │   ├── java/com/thinkforge/quiz_service/
│   │   │   ├── controller/         # REST APIs
│   │   │   ├── service/            # Quiz + Python bridge
│   │   │   ├── entity/             # JPA models
│   │   │   ├── dto/                # Request/response DTOs
│   ├── resources/
│   │   └── application.properties  # Config
├── generate.py                    # LangChain-Powered Quiz Script
├── requirements.txt              # Python deps (LangChain, dotenv, etc.)
├── Dockerfile                    # Multi-stage Java + Python image
├── .env                          # Environment variables (not committed)
├── README.md                     # You're here!
```

---

## 🔐 Environment Variables

Configure these securely via `.env` (dev) or Railway's environment tab:

```
GEMINI_API_KEY=your_gemini_api_key
DATABASE_URL=jdbc:postgresql://your-host:5432/postgres
DATABASE_USERNAME=your_db_user
DATABASE_PASSWORD=your_db_pass
```

> ⚠️ Never commit `.env` — it’s in `.gitignore`.

---

## 📦 How to Build and Run

### 🖥️ 1. Clone the repo

```bash
git clone https://github.com/Arghya-Banerjee/quiz-service.git
cd quiz-service
```

### 🐍 2. Set up Python (optional for development)

```bash
python3 -m venv .venv
source .venv/bin/activate
pip install -r requirements.txt
```

### 🐳 3. Docker Build & Run (Production mode)

```bash
docker build -t quiz-service .
docker run -p 8080:8080 \
  -e GEMINI_API_KEY=your_key \
  -e DATABASE_URL=your_url \
  -e DATABASE_USERNAME=your_user \
  -e DATABASE_PASSWORD=your_pass \
  quiz-service
```

---

## 🔗 API Endpoints

| Method | Endpoint                       | Description                             |
|--------|--------------------------------|-----------------------------------------|
| POST   | `/api/v1/quiz/generate`        | 🧠 Generate AI-powered quiz questions    |
| GET    | `/api/v1/quiz/all`             | 📚 List all quizzes                     |
| GET    | `/api/v1/quiz/{quizId}`        | 🔍 Get quiz by ID                       |
| GET    | `/api/v1/quiz/teacher/{id}`    | 👤 Get quizzes by teacher               |
| PUT    | `/api/v1/quiz/{quizId}`        | ✏️ Update quiz details                  |
| DELETE | `/api/v1/quiz/{quizId}`        | ❌ Delete quiz                          |
| POST   | `/api/v1/quiz/{quizId}/submit` | 📝 Submit quiz answers                  |
| GET    | `/api/v1/quiz/quiz-analysis/{quizId}` | 📊 Quiz analytics                   |

---

## 🧠 How Quiz Generation Works

1. User sends a `POST` request to `/generate` with grade, subject, topic, and question count.
2. Spring Boot service calls `generate.py` via `ProcessBuilder`.
3. Python script uses LangChain + Gemini to create MCQs.
4. The response is validated, cleaned (via regex & JSON fixes), and returned to the frontend.

---

## 🧪 Sample Request

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

## ✅ TODO / Improvements

- [ ] Add Swagger/OpenAPI documentation
- [ ] Unit tests for Python script and Java services
- [ ] Caching for repeated quiz generations
- [ ] Admin panel or UI (React/Next.js)

---

## 🙏 Credits

- Built with ❤️ by [Arghya-Banerjee](https://github.com/Arghya-Banerjee)
- Powered by [Google Gemini](https://ai.google.dev/)
- Inspired by real classroom needs for auto-assessment

---

## 📄 License

MIT License. See `LICENSE` file.
