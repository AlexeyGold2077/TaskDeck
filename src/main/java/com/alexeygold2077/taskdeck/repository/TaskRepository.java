package com.alexeygold2077.taskdeck.repository;

import com.alexeygold2077.taskdeck.model.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
