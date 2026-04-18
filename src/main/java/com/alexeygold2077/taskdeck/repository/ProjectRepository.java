package com.alexeygold2077.taskdeck.repository;

import com.alexeygold2077.taskdeck.model.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findAllByCreatedBy_Id(Long createdById);
    Optional<Project> findByIdAndCreatedBy_Id(Long id, Long createdById);
}
