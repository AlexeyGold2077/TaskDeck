package com.alexeygold2077.taskdeck.model.dto;

import com.alexeygold2077.taskdeck.model.entity.Project;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GetAllProjectsResponseDto {
    List<Project> projects;
}
