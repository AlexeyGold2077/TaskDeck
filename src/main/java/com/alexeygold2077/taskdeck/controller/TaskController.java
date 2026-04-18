package com.alexeygold2077.taskdeck.controller;

import com.alexeygold2077.taskdeck.model.dto.CreateTaskRequestDto;
import com.alexeygold2077.taskdeck.model.dto.TaskDTO;
import com.alexeygold2077.taskdeck.model.dto.UpdateTaskStatusDto;
import com.alexeygold2077.taskdeck.model.entity.User;
import com.alexeygold2077.taskdeck.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/projects/{projectId}/tasks")
    @ResponseStatus(HttpStatus.CREATED)
    public TaskDTO createTask(
            @AuthenticationPrincipal User user,
            @PathVariable Long projectId,
            @RequestBody @Valid CreateTaskRequestDto request
    ) {
        return taskService.createTask(user.getId(), projectId, request);
    }

    @GetMapping("/projects/{projectId}/tasks")
    public List<TaskDTO> getTasksByProject(
            @AuthenticationPrincipal User user,
            @PathVariable Long projectId
    ) {
        return taskService.getTasksByProject(user.getId(), projectId);
    }

    @PatchMapping("/tasks/{id}")
    public TaskDTO updateTaskStatus(
            @AuthenticationPrincipal User user,
            @PathVariable Long id,
            @RequestBody @Valid UpdateTaskStatusDto request
    ) {
        return taskService.updateTaskStatus(user.getId(), id, request);
    }

    @DeleteMapping("/tasks/{id}")
    public void deleteTask(
            @AuthenticationPrincipal User user,
            @PathVariable Long id
    ) {
        taskService.deleteTask(user.getId(), id);
    }
}
