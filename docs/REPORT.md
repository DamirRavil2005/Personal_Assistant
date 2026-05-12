# Отчёт по проекту «Персональный ассистент»

## 1. Описание проекта

Веб-приложение для управления личными задачами с возможностью регистрации, аутентификации и CRUD-операций. Каждый пользователь видит только свои задачи. Реализована современная архитектура: REST API на Spring Boot 3 + SPA на React.

## 2. Технологический стек

### Backend
- **Язык:** Java 21
- **Фреймворк:** Spring Boot 3.5.14
- **Безопасность:** Spring Security + JWT (jjwt 0.12.6), BCrypt
- **БД:** PostgreSQL 16 (через JPA/Hibernate)
- **Миграции:** Flyway
- **Кэш:** Redis 7
- **API-документация:** SpringDoc OpenAPI 2.8 (Swagger UI)
- **Утилиты:** MapStruct (DTO-маппинг), Lombok

### Frontend
- **Фреймворк:** React 18 + TypeScript
- **Сборка:** Vite
- **Стили:** Tailwind CSS
- **HTTP:** Axios
- **Роутинг:** React Router 6

### Инфраструктура
- **Контейнеризация:** Docker, Docker Compose
- **VCS:** Git, GitHub
- **Ветвление:** main / dev / feature/*

## 3. Архитектура

Применена **Clean Architecture** с разделением на слои:
Controller (REST) → Service (бизнес-логика) → Repository (JPA) → Domain (Entity)
↓
DTO + Mapper

### Пакеты backend:
- `config/` — `SecurityConfig`, `OpenApiConfig`
- `domain/entity/` — JPA-сущности (`User`, `Task`)
- `domain/enums/` — `Role`, `TaskStatus`, `TaskPriority`
- `repository/` — Spring Data JPA репозитории
- `service/` — `AuthService`, `TaskService`
- `web/controller/` — REST-контроллеры
- `web/dto/` — DTO (Java records)
- `mapper/` — MapStruct мапперы
- `security/` — `JwtService`, `JwtAuthenticationFilter`, `CustomUserDetailsService`
- `exception/` — `GlobalExceptionHandler`, кастомные исключения

## 4. База данных

### Схема

| Таблица | Назначение |
|---|---|
| `users` | Пользователи (id, username, email, password_hash, role, enabled, timestamps) |
| `tasks` | Задачи (id, title, description, status, priority, due_date, user_id, timestamps) |
| `flyway_schema_history` | История миграций |

### Миграции (Flyway)
- `V1__init.sql` — начальная инициализация
- `V2__create_users_table.sql` — таблица пользователей
- `V3__create_tasks_table.sql` — таблица задач с FK на users

## 5. Безопасность

- **JWT-токены**: access (15 мин) + refresh (7 дней)
- **BCrypt** хеширование паролей
- **Stateless-сессии** (Spring Security)
- **RBAC**: роли `USER` / `ADMIN`
- **ABAC**: задачи фильтруются по `user_id` — изоляция данных
- **CORS** настроен для localhost:5173
- **CSRF** отключён (stateless REST API)
- **Глобальный exception handler** — единый формат ошибок

## 6. REST API

| Метод | Эндпоинт | Описание | Авторизация |
|---|---|---|---|
| POST | `/api/auth/register` | Регистрация | — |
| POST | `/api/auth/login` | Логин, выдача JWT | — |
| GET | `/api/tasks` | Список задач (пагинация, фильтр по статусу) | JWT |
| GET | `/api/tasks/{id}` | Получить задачу | JWT |
| POST | `/api/tasks` | Создать задачу | JWT |
| PUT | `/api/tasks/{id}` | Обновить задачу | JWT |
| DELETE | `/api/tasks/{id}` | Удалить задачу | JWT |

Полная документация: **http://localhost:8080/swagger-ui.html**

## 7. Frontend — функциональность

- **`/login`** — форма входа
- **`/register`** — форма регистрации
- **`/tasks`** — главная страница (защищённый маршрут):
  - Список всех задач пользователя
  - Создание задачи с выбором приоритета
  - Изменение статуса (dropdown)
  - Удаление задачи
  - Цветовая индикация приоритета/статуса

JWT-токен хранится в `localStorage`. Axios-interceptor автоматически добавляет заголовок `Authorization: Bearer <token>` и редиректит на `/login` при 401.

## 8. Запуск проекта

### Требования
- Java 21, Maven 3.9+, Node.js 22, Docker Desktop

### Шаги
```bash
# 1. Запустить инфраструктуру
docker compose up -d

# 2. Backend
cd backend
mvn clean package -DskipTests
java -jar target/personal-assistant-0.0.1-SNAPSHOT.jar
# → http://localhost:8080

# 3. Frontend
cd frontend
npm install
npm run dev
# → http://localhost:5173
```

## 9. Git workflow

- Ветка `main` — production-ready
- Ветка `dev` — активная разработка
- Коммиты с префиксами: `feat:`, `fix:`, `refactor:`, `docs:`
- Все изменения через Pull Request (для критических веток)

Репозиторий: **https://github.com/DamirRavil2005/Personal_Assistant**

## 10. Что реализовано / TODO

### Реализовано
- Чистая многослойная архитектура (Clean Architecture)
- JWT-аутентификация, BCrypt, RBAC + ABAC
- Полный CRUD для задач
- Изоляция данных между пользователями
- Flyway-миграции БД
- Swagger UI с авторизацией
- React SPA с роутингом и защищёнными страницами
- Docker Compose для PostgreSQL + Redis
- README + структурированный отчёт

### Следующие шаги (вне рамок сдачи)
- Unit + интеграционные тесты (JUnit 5, Testcontainers)
- Dockerfile для backend/frontend, Kubernetes-манифесты
- GitHub Actions CI/CD (build → test → docker)
- Audit logs, refresh-token flow
- Нагрузочное тестирование (k6)

## Автор

DamirRavil2005, AITU