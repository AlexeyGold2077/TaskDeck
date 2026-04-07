package com.alexeygold2077.taskdeck.repository;

import com.alexeygold2077.taskdeck.model.entity.Project;
import com.alexeygold2077.taskdeck.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findAllByCreatedBy_Id(Long createdById);
}
