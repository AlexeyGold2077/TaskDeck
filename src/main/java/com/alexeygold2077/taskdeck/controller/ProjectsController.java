package com.alexeygold2077.taskdeck.controller;

import com.alexeygold2077.taskdeck.model.dto.CreateProjectRequestDto;
import com.alexeygold2077.taskdeck.model.dto.ProjectDTO;
import com.alexeygold2077.taskdeck.model.entity.User;
import com.alexeygold2077.taskdeck.service.ProjectsService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/projects")
public class ProjectsController {

    private final ProjectsService projectsService;

    public ProjectsController(ProjectsService projectsService) {
        this.projectsService = projectsService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProjectDTO createProject(
            @AuthenticationPrincipal User user,
            @Valid @RequestBody CreateProjectRequestDto request
    ) {
        return projectsService.createProject(user.getId(), request);
    }

    @GetMapping
    public List<ProjectDTO> getAllProjects(@AuthenticationPrincipal User user) {
        return projectsService.getAllProjects(user.getId());
    }

    @GetMapping("/{id}")
    public ProjectDTO getProjectById(
            @AuthenticationPrincipal User user,
            @PathVariable Long id
    ) {
        return projectsService.getProjectById(user.getId(), id);
    }

    @DeleteMapping("/{id}")
    public ProjectDTO deleteProjectById(
            @AuthenticationPrincipal User user,
            @PathVariable Long id
    ) {
        return projectsService.deleteProjectById(user.getId(), id);
    }
}
