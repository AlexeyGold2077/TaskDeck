package com.alexeygold2077.taskdeck.mapper;

import com.alexeygold2077.taskdeck.model.dto.TaskDTO;
import com.alexeygold2077.taskdeck.model.entity.Project;
import com.alexeygold2077.taskdeck.model.entity.Task;
import com.alexeygold2077.taskdeck.model.entity.User;

import java.util.List;

public class TaskMapper {

    public static TaskDTO toDTO(Task task) {
        if (task == null) return null;

        return new TaskDTO(
                task.getId(),
                task.getName(),
                task.getDescription(),
                task.getCreatedAt(),
                task.getDueDate(),
                task.getStatus(),
                task.getPriority(),
                task.getProject() != null ? task.getProject().getId() : null,
                task.getCreatedBy() != null ? task.getCreatedBy().getId() : null,
                task.getExecutor() != null ? task.getExecutor().getId() : null
        );
    }

    public static List<TaskDTO> toDTOList(List<Task> tasks) {
        if (tasks == null) return List.of();

        List<TaskDTO> result = new java.util.ArrayList<>(tasks.size());

        for (Task task : tasks) {
            result.add(toDTO(task));
        }

        return result;
    }

    public static Task toEntity(
            TaskDTO dto,
            Project project,
            User createdBy,
            User executor
    ) {
        if (dto == null) return null;

        Task task = new Task();
        task.setId(dto.getId());
        task.setName(dto.getName());
        task.setDescription(dto.getDescription());
        task.setCreatedAt(dto.getCreatedAt());
        task.setDueDate(dto.getDueDate());
        task.setStatus(dto.getStatus());
        task.setPriority(dto.getPriority());

        task.setProject(project);
        task.setCreatedBy(createdBy);
        task.setExecutor(executor);

        return task;
    }
}
