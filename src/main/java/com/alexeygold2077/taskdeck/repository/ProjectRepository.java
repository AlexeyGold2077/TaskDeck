package com.alexeygold2077.taskdeck.repository;

import com.alexeygold2077.taskdeck.model.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}
