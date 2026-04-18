package com.alexeygold2077.taskdeck.repository;

import com.alexeygold2077.taskdeck.model.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findAllByProject_IdAndProject_CreatedBy_Id(Long projectId, Long createdById);
    Optional<Task> findByIdAndProject_CreatedBy_Id(Long id, Long createdById);
    void deleteAllByProject_IdAndProject_CreatedBy_Id(Long projectId, Long createdById);
}
