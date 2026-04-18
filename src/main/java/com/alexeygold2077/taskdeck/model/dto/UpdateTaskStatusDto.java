package com.alexeygold2077.taskdeck.model.dto;

import com.alexeygold2077.taskdeck.model.entity.Status;
import lombok.Data;
import jakarta.validation.constraints.NotNull;

@Data
public class UpdateTaskStatusDto {
    @NotNull
    Status newStatus;
}
