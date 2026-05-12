package kz.aitu.assistant.service;

import kz.aitu.assistant.domain.entity.Task;
import kz.aitu.assistant.domain.entity.User;
import kz.aitu.assistant.domain.enums.TaskStatus;
import kz.aitu.assistant.exception.NotFoundException;
import kz.aitu.assistant.mapper.TaskMapper;
import kz.aitu.assistant.repository.TaskRepository;
import kz.aitu.assistant.security.SecurityUtils;
import kz.aitu.assistant.web.dto.TaskRequest;
import kz.aitu.assistant.web.dto.TaskResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final SecurityUtils securityUtils;

    @Transactional(readOnly = true)
    public Page<TaskResponse> getAll(TaskStatus status, Pageable pageable) {
        User user = securityUtils.getCurrentUser();
        Page<Task> tasks = (status == null)
                ? taskRepository.findByUserId(user.getId(), pageable)
                : taskRepository.findByUserIdAndStatus(user.getId(), status, pageable);
        return tasks.map(taskMapper::toResponse);
    }

    @Transactional(readOnly = true)
    public TaskResponse getById(Long id) {
        User user = securityUtils.getCurrentUser();
        Task task = taskRepository.findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new NotFoundException("Task not found: " + id));
        return taskMapper.toResponse(task);
    }

    @Transactional
    public TaskResponse create(TaskRequest request) {
        User user = securityUtils.getCurrentUser();
        Task task = taskMapper.toEntity(request);
        task.setUser(user);
        Task saved = taskRepository.save(task);
        return taskMapper.toResponse(saved);
    }

    @Transactional
    public TaskResponse update(Long id, TaskRequest request) {
        User user = securityUtils.getCurrentUser();
        Task task = taskRepository.findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new NotFoundException("Task not found: " + id));
        taskMapper.updateEntity(task, request);
        return taskMapper.toResponse(task);
    }

    @Transactional
    public void delete(Long id) {
        User user = securityUtils.getCurrentUser();
        Task task = taskRepository.findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new NotFoundException("Task not found: " + id));
        taskRepository.delete(task);
    }
}