# Personal Assistant

Веб-приложение «Персональный ассистент» для управления задачами с JWT-аутентификацией.

## Технологии

**Backend:**
- Java 21, Spring Boot 3.5
- Spring Security + JWT (jjwt 0.12)
- Spring Data JPA + PostgreSQL 16
- Flyway (миграции БД)
- Redis (кэш)
- MapStruct, Lombok
- SpringDoc OpenAPI (Swagger UI)
- Clean Architecture: Controller → Service → Repository → Domain

**Frontend:**
- React 18 + Vite + TypeScript
- Tailwind CSS
- Axios + TanStack Query
- React Router

**DevOps:**
- Docker / Docker Compose
- GitHub Actions (CI/CD)

## Архитектура
backend/
├── src/main/java/kz/aitu/assistant/
│   ├── config/          # SecurityConfig, OpenApiConfig
│   ├── domain/
│   │   ├── entity/      # User, Task
│   │   └── enums/       # Role, TaskStatus, TaskPriority
│   ├── repository/      # Spring Data JPA репозитории
│   ├── service/         # бизнес-логика
│   ├── web/
│   │   ├── controller/  # REST контроллеры
│   │   └── dto/         # DTO (records)
│   ├── mapper/          # MapStruct мапперы
│   ├── security/        # JwtService, JwtAuthFilter, UserDetailsService
│   └── exception/       # GlobalExceptionHandler
└── src/main/resources/
├── application.properties
└── db/migration/    # Flyway-миграции (V1, V2, V3)

## Возможности

- Регистрация и аутентификация пользователей (JWT access + refresh токены)
- BCrypt-хеширование паролей
- Роли пользователей (USER / ADMIN), RBAC
- CRUD задач: создание, чтение, обновление, удаление
- Изоляция данных: каждый пользователь видит только свои задачи
- Фильтрация задач по статусу, пагинация, сортировка
- Аудит: автоматические `created_at` / `updated_at`
- Глобальная обработка ошибок с понятными HTTP-кодами
- Документация API через Swagger UI

## Запуск

### Требования
- Java 21
- Maven 3.9+
- Docker Desktop

### 1. Запустить инфраструктуру (PostgreSQL + Redis)
```bash
docker compose up -d
```

### 2. Запустить backend
```bash
cd backend
mvn clean package -DskipTests
java -jar target/personal-assistant-0.0.1-SNAPSHOT.jar
```

Приложение будет доступно на `http://localhost:8080`.

### 3. Swagger UI
http://localhost:8080/swagger-ui.html

## Примеры API

### Регистрация
```bash
POST /api/auth/register
{
  "username": "damir",
  "email": "damir@test.kz",
  "password": "password123",
  "fullName": "Damir Ravil"
}
```

### Логин
```bash
POST /api/auth/login
{ "username": "damir", "password": "password123" }
```
Ответ:
```json
{ "accessToken": "eyJ...", "refreshToken": "eyJ...", "username": "damir", "role": "USER" }
```

### Создать задачу (требуется JWT)
```bash
POST /api/tasks
Authorization: Bearer <accessToken>
{
  "title": "Подготовить отчёт",
  "priority": "HIGH",
  "status": "TODO",
  "dueDate": "2026-05-20T18:00:00"
}
```

## Структура БД

- `users` — пользователи (id, username, email, password_hash, role, enabled, timestamps)
- `tasks` — задачи (id, title, description, status, priority, due_date, user_id, timestamps)
- `flyway_schema_history` — служебная

## Автор
DamirRavil2005