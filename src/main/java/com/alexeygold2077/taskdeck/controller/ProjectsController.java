package com.alexeygold2077.taskdeck.controller;

import com.alexeygold2077.taskdeck.model.dto.*;
import com.alexeygold2077.taskdeck.model.entity.User;
import com.alexeygold2077.taskdeck.service.ProjectsService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projects")
public class ProjectsController {

    private final ProjectsService projectsService;

    public ProjectsController(ProjectsService projectsService) {
        this.projectsService = projectsService;
    }

    @PostMapping
    public void createProject(@AuthenticationPrincipal User user,
                              @Valid @RequestBody CreateProjectRequestDto request) {
        projectsService.createProject(user, request);
    }

    @GetMapping
    public List<ProjectDTO> getAllProjects(@AuthenticationPrincipal User user) {
        return projectsService.getAllProjects(user.getId());
    }

    @GetMapping("/{id}")
    public ProjectDTO getProjectById(@AuthenticationPrincipal User user,
                                     @PathVariable Long id) {
        return projectsService.getProjectById(id);
    }


    @DeleteMapping("/{id}")
    public ProjectDTO deleteProjectById(@AuthenticationPrincipal User user,
                                        @PathVariable Long id) {
        return projectsService.deleteProjectById(id);
    }
}
