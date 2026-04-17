package com.alexeygold2077.taskdeck.service;

import com.alexeygold2077.taskdeck.model.dto.CreateProjectRequestDto;
import com.alexeygold2077.taskdeck.model.dto.GetAllProjectsResponseDto;
import com.alexeygold2077.taskdeck.model.dto.GetProjectByIdRequestDto;
import com.alexeygold2077.taskdeck.model.dto.ProjectDTO;
import com.alexeygold2077.taskdeck.model.entity.Project;
import com.alexeygold2077.taskdeck.model.entity.User;
import com.alexeygold2077.taskdeck.model.util.ProjectMapper;
import com.alexeygold2077.taskdeck.repository.ProjectRepository;
import com.alexeygold2077.taskdeck.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

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

    public List<ProjectDTO> getAllProjects(Long userId) {

        List<Project> projects = projectRepository.findAllByCreatedBy_Id(userId);

        return ProjectMapper.toDTOList(projects);
    }

    public ProjectDTO getProjectById(Long id) {

        Optional<Project> project = projectRepository.findById(id);

        if (project.isEmpty())
            return null;

        return ProjectMapper.toDTO(project.get());
    }
}
