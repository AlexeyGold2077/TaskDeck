package com.alexeygold2077.taskdeck.model.dto;

import com.alexeygold2077.taskdeck.model.entity.Priority;
import com.alexeygold2077.taskdeck.model.entity.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDTO {

    private Long id;
    private String name;
    private String description;

    private Long createdAt;
    private Long dueDate;

    private Status status;
    private Priority priority;

    private Long projectId;
    private Long createdById;
    private Long executorId;
}
