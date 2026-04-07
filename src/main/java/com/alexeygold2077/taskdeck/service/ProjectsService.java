package com.alexeygold2077.taskdeck.service;

import com.alexeygold2077.taskdeck.model.dto.CreateProjectRequestDto;
import com.alexeygold2077.taskdeck.model.dto.GetAllProjectsResponseDto;
import com.alexeygold2077.taskdeck.model.entity.Project;
import com.alexeygold2077.taskdeck.model.entity.User;
import com.alexeygold2077.taskdeck.repository.ProjectRepository;
import com.alexeygold2077.taskdeck.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class ProjectsService {

    private final ProjectRepository projectRepository;

    public ProjectsService(
            ProjectRepository projectRepository
    ) {
        this.projectRepository = projectRepository;
    }

    public void createProject(User user, CreateProjectRequestDto request) {
        Project project = new Project(
                request.getName(),
                request.getDescription(),
                user
        );

        project.setCreatedAt((int) Instant.now().getEpochSecond());

        projectRepository.save(project);
    }

    public GetAllProjectsResponseDto getAllProjects(Long userId) {

        List<Project> projects = projectRepository.findAllByCreatedBy_Id(userId);

        return new GetAllProjectsResponseDto(
                projects
        );
    }
}
