package com.alexeygold2077.taskdeck.model.dto;

import com.alexeygold2077.taskdeck.model.entity.Priority;
import com.alexeygold2077.taskdeck.model.entity.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
public class CreateTaskRequestDto {

    @NotBlank
    private String name;

    private String description;

    @NotNull
    private Priority priority;

    private Status status;

    @NotNull
    private Integer dueDate;
}
