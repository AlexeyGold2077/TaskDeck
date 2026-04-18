package com.alexeygold2077.taskdeck.repository;

import com.alexeygold2077.taskdeck.model.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findAllByProject_Id(Long projectId);
}
