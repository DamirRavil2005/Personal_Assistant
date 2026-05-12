package kz.aitu.assistant.web.dto;

import kz.aitu.assistant.domain.enums.TaskPriority;
import kz.aitu.assistant.domain.enums.TaskStatus;

import java.time.LocalDateTime;

public record TaskResponse(
        Long id,
        String title,
        String description,
        TaskStatus status,
        TaskPriority priority,
        LocalDateTime dueDate,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}