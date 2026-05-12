# REST API Documentation

Base URL: `http://localhost:8080`

## Authentication

### POST /api/auth/register
Регистрация нового пользователя.

**Request:**
```json
{
  "username": "damir",
  "email": "damir@test.kz",
  "password": "password123",
  "fullName": "Damir Ravil"
}
```

**Response 200:**
```json
{
  "accessToken": "eyJhbGc...",
  "refreshToken": "eyJhbGc...",
  "username": "damir",
  "role": "USER"
}
```

### POST /api/auth/login
Логин по username + password.

## Tasks (требуется JWT)

### GET /api/tasks
Список задач текущего пользователя (с пагинацией).

**Query params:**
- `status` (опционально): TODO | IN_PROGRESS | DONE | CANCELLED
- `page`, `size`, `sort`

### POST /api/tasks
Создание задачи.

### PUT /api/tasks/{id}
Обновление задачи.

### DELETE /api/tasks/{id}
Удаление задачи.

## Полная документация

Интерактивная документация Swagger UI: `http://localhost:8080/swagger-ui.html`