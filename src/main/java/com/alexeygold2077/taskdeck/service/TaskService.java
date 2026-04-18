package com.alexeygold2077.taskdeck.service;

import com.alexeygold2077.taskdeck.model.dto.CreateTaskRequestDto;
import com.alexeygold2077.taskdeck.model.dto.TaskDTO;
import com.alexeygold2077.taskdeck.model.entity.*;
import com.alexeygold2077.taskdeck.model.util.TaskMapper;
import com.alexeygold2077.taskdeck.repository.ProjectRepository;
import com.alexeygold2077.taskdeck.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;

    public TaskService(
            TaskRepository taskRepository,
            ProjectRepository projectRepository
    ) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
    }

    public void createTask(User user, Long projectId, CreateTaskRequestDto request) {
        Task task = Task.builder()
                .name(request.getName())
                .description(request.getDescription())
                .createdAt((int) Instant.now().getEpochSecond())
                .status(request.getStatus())
                .priority(request.getPriority())
                .dueDate(request.getDueDate())
                .project(projectRepository.findById(projectId).get())
                .createdBy(user)
                .executor(null)
                .build();

        taskRepository.save(task);
    }

    public List<TaskDTO> getTasksByProject(User user, Long projectId) {
        return TaskMapper.toDTOList(taskRepository.findAllByProject_Id(projectId));
    }
}
