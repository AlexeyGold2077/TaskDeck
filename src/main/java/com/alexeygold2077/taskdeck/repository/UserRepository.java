package com.alexeygold2077.taskdeck.repository;

import com.alexeygold2077.taskdeck.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
