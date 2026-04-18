package com.alexeygold2077.taskdeck.model.dto;

import com.alexeygold2077.taskdeck.model.entity.Status;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class UpdateTaskStatusDto {
    Status newStatus;
}
