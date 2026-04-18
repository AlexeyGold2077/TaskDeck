package com.alexeygold2077.taskdeck.service;

import com.alexeygold2077.taskdeck.exception.ResourceNotFoundException;
import com.alexeygold2077.taskdeck.model.dto.CreateProjectRequestDto;
import com.alexeygold2077.taskdeck.model.dto.ProjectDTO;
import com.alexeygold2077.taskdeck.model.entity.Project;
import com.alexeygold2077.taskdeck.model.util.ProjectMapper;
import com.alexeygold2077.taskdeck.repository.ProjectRepository;
import com.alexeygold2077.taskdeck.repository.TaskRepository;
import com.alexeygold2077.taskdeck.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
public class ProjectsService {

    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public ProjectsService(
            ProjectRepository projectRepository,
            TaskRepository taskRepository,
            UserRepository userRepository
    ) {
        this.projectRepository = projectRepository;
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    public ProjectDTO createProject(Long userId, CreateProjectRequestDto request) {
        Project project = new Project(
                request.getName(),
                request.getDescription(),
                userRepository.getReferenceById(userId)
        );
        project.setCreatedAt(Instant.now().getEpochSecond());

        return ProjectMapper.toDTO(projectRepository.save(project));
    }

    public List<ProjectDTO> getAllProjects(Long userId) {
        List<Project> projects = projectRepository.findAllByCreatedBy_Id(userId);
        return ProjectMapper.toDTOList(projects);
    }

    public ProjectDTO getProjectById(Long userId, Long id) {
        Project project = projectRepository.findByIdAndCreatedBy_Id(id, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));
        return ProjectMapper.toDTO(project);
    }

    @Transactional
    public ProjectDTO deleteProjectById(Long userId, Long id) {
        Project project = projectRepository.findByIdAndCreatedBy_Id(id, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));
        taskRepository.deleteAllByProject_IdAndProject_CreatedBy_Id(id, userId);
        projectRepository.delete(project);
        return ProjectMapper.toDTO(project);
    }
}
