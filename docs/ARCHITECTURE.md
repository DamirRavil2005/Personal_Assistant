# Архитектура проекта

## Принципы

- **Clean Architecture** — разделение на слои Controller → Service → Repository → Domain
- **DTO Pattern** — изоляция доменной модели от внешнего API
- **Dependency Injection** через Spring IoC контейнер
- **Stateless REST** — сервер не хранит состояние сессий

## Слои Backend

### 1. Web Layer (`web/`)
- Контроллеры (`@RestController`)
- DTO (Java records для immutability)
- Валидация через `@Valid` + jakarta.validation

### 2. Service Layer (`service/`)
- Бизнес-логика
- Транзакции (`@Transactional`)
- Использование MapStruct для маппинга

### 3. Repository Layer (`repository/`)
- Spring Data JPA репозитории
- Готовые методы (`findByUsername`, `existsByEmail`)

### 4. Domain Layer (`domain/`)
- JPA-сущности с аудитом (`@CreatedDate`, `@LastModifiedDate`)
- Enums: Role, TaskStatus, TaskPriority

## Безопасность