package com.alexeygold2077.taskdeck.controller;

import com.alexeygold2077.taskdeck.model.dto.CreateTaskRequestDto;
import com.alexeygold2077.taskdeck.model.dto.TaskDTO;
import com.alexeygold2077.taskdeck.model.dto.UpdateTaskStatusDto;
import com.alexeygold2077.taskdeck.model.entity.User;
import com.alexeygold2077.taskdeck.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/projects/{projectId}/tasks")
    public void createTask(
            @AuthenticationPrincipal User user,
            @PathVariable Long projectId,
            @RequestBody @Valid CreateTaskRequestDto request
    ) {
        taskService.createTask(user, projectId, request);
    }

    // Получение задач проекта
    @GetMapping("/projects/{projectId}/tasks")
    public List<TaskDTO> getTasksByProject(
            @AuthenticationPrincipal User user,
            @PathVariable Long projectId
    ) {
        return taskService.getTasksByProject(user, projectId);
    }

    @PatchMapping("/tasks/{id}")
    public TaskDTO updateTaskStatus(
            @AuthenticationPrincipal User user,
            @PathVariable Long id,
            @RequestBody UpdateTaskStatusDto request
    ) {
        return taskService.updateTaskStatus(id, request);
    }

    @DeleteMapping("/tasks/{id}")
    public void deleteTask(
            @AuthenticationPrincipal User user,
            @PathVariable Long id
    ) {
        taskService.deleteTask(id);
    }
}
