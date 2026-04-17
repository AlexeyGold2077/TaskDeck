package com.alexeygold2077.taskdeck.model.dto;

import com.alexeygold2077.taskdeck.model.entity.Project;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetProjectByIdRequestDto {
    Project project;
}
