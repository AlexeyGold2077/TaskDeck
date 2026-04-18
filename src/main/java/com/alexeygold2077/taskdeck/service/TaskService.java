package com.alexeygold2077.taskdeck.service;

import com.alexeygold2077.taskdeck.exception.ResourceNotFoundException;
import com.alexeygold2077.taskdeck.model.dto.CreateTaskRequestDto;
import com.alexeygold2077.taskdeck.model.dto.TaskDTO;
import com.alexeygold2077.taskdeck.model.dto.UpdateTaskStatusDto;
import com.alexeygold2077.taskdeck.model.entity.Project;
import com.alexeygold2077.taskdeck.model.entity.Status;
import com.alexeygold2077.taskdeck.model.entity.Task;
import com.alexeygold2077.taskdeck.model.util.TaskMapper;
import com.alexeygold2077.taskdeck.repository.ProjectRepository;
import com.alexeygold2077.taskdeck.repository.TaskRepository;
import com.alexeygold2077.taskdeck.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public TaskService(
            TaskRepository taskRepository,
            ProjectRepository projectRepository,
            UserRepository userRepository
    ) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    public TaskDTO createTask(Long userId, Long projectId, CreateTaskRequestDto request) {
        Project project = projectRepository.findByIdAndCreatedBy_Id(projectId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));

        Task task = Task.builder()
                .name(request.getName())
                .description(request.getDescription())
                .createdAt(Instant.now().getEpochSecond())
                .status(request.getStatus() != null ? request.getStatus() : Status.NEW)
                .priority(request.getPriority())
                .dueDate(request.getDueDate())
                .project(project)
                .createdBy(userRepository.getReferenceById(userId))
                .executor(null)
                .build();

        return TaskMapper.toDTO(taskRepository.save(task));
    }

    public List<TaskDTO> getTasksByProject(Long userId, Long projectId) {
        projectRepository.findByIdAndCreatedBy_Id(projectId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));
        return TaskMapper.toDTOList(taskRepository.findAllByProject_IdAndProject_CreatedBy_Id(projectId, userId));
    }

    public TaskDTO updateTaskStatus(Long userId, Long id, UpdateTaskStatusDto request) {
        Task task = taskRepository.findByIdAndProject_CreatedBy_Id(id, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));
        task.setStatus(request.getNewStatus());
        return TaskMapper.toDTO(taskRepository.save(task));
    }

    public void deleteTask(Long userId, Long id) {
        Task task = taskRepository.findByIdAndProject_CreatedBy_Id(id, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));
        taskRepository.delete(task);
    }
}
