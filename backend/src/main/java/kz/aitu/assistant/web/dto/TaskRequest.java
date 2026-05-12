package kz.aitu.assistant.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import kz.aitu.assistant.domain.enums.TaskPriority;
import kz.aitu.assistant.domain.enums.TaskStatus;

import java.time.LocalDateTime;

public record TaskRequest(
        @NotBlank @Size(max = 255) String title,
        String description,
        TaskStatus status,
        TaskPriority priority,
        LocalDateTime dueDate
) {}