package com.alexeygold2077.taskdeck.model.util;

import com.alexeygold2077.taskdeck.model.dto.ProjectDTO;
import com.alexeygold2077.taskdeck.model.entity.Project;

import java.util.ArrayList;
import java.util.List;

public class ProjectMapper {

    public static ProjectDTO toDTO(Project project) {
        if (project == null) {
            return null;
        }

        return new ProjectDTO(
                project.getId(),
                project.getName(),
                project.getDescription(),
                project.getCreatedAt(),
                project.getCreatedBy() != null ? project.getCreatedBy().getId() : null
        );
    }

    public static List<ProjectDTO> toDTOList(List<Project> projects) {
        List<ProjectDTO> dtoList = new ArrayList<>();

        for (Project project : projects) {
            dtoList.add(toDTO(project));
        }

        return dtoList;
    }
}
