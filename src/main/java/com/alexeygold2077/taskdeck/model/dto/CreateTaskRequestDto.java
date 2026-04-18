package com.alexeygold2077.taskdeck.model.dto;

import com.alexeygold2077.taskdeck.model.entity.Priority;
import com.alexeygold2077.taskdeck.model.entity.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.Instant;

@Data
public class CreateTaskRequestDto {

    @NotBlank
    @Size(max = 255)
    private String name;

    @Size(max = 4000)
    private String description;

    @NotNull
    private Priority priority;

    private Status status;

    @NotNull
    private Instant dueDate;
}
