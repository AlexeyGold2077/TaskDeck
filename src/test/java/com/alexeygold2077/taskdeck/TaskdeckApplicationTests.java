package com.alexeygold2077.taskdeck;

import com.alexeygold2077.taskdeck.exception.ResourceNotFoundException;
import com.alexeygold2077.taskdeck.model.dto.CreateTaskRequestDto;
import com.alexeygold2077.taskdeck.model.dto.TaskDTO;
import com.alexeygold2077.taskdeck.model.dto.UpdateTaskStatusDto;
import com.alexeygold2077.taskdeck.model.entity.Priority;
import com.alexeygold2077.taskdeck.model.entity.Project;
import com.alexeygold2077.taskdeck.model.entity.Role;
import com.alexeygold2077.taskdeck.model.entity.User;
import com.alexeygold2077.taskdeck.repository.ProjectRepository;
import com.alexeygold2077.taskdeck.repository.TaskRepository;
import com.alexeygold2077.taskdeck.repository.UserRepository;
import com.alexeygold2077.taskdeck.service.ProjectsService;
import com.alexeygold2077.taskdeck.service.TaskService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Instant;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class TaskdeckApplicationTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ProjectsService projectsService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private Validator validator;

    @BeforeEach
    void setUp() {
        taskRepository.deleteAll();
        projectRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void shouldDenyAccessToForeignProject() {
        User owner = saveUser("owner@example.com", "owner");
        User intruder = saveUser("intruder@example.com", "intruder");
        Project project = projectRepository.save(new Project(
                null,
                "Owner project",
                "Private",
                Instant.parse("2024-05-06T11:06:40Z"),
                owner
        ));

        assertThrows(
                ResourceNotFoundException.class,
                () -> projectsService.getProjectById(intruder.getId(), project.getId())
        );
    }

    @Test
    void shouldDefaultTaskStatusToNew() {
        User owner = saveUser("task-owner@example.com", "taskowner");
        Project project = projectRepository.save(new Project(
                null,
                "Backend",
                "Tasks",
                Instant.parse("2024-05-06T11:06:40Z"),
                owner
        ));

        CreateTaskRequestDto request = new CreateTaskRequestDto();
        request.setName("Write tests");
        request.setDescription("Cover critical flows");
        request.setPriority(Priority.HIGH);
        request.setDueDate(Instant.parse("2024-05-06T11:08:20Z"));

        TaskDTO task = taskService.createTask(owner.getId(), project.getId(), request);

        assertEquals(project.getId(), task.getProjectId());
        assertEquals("NEW", task.getStatus().name());
    }

    @Test
    void shouldRejectNullTaskStatusUpdateAtValidationLayer() {
        UpdateTaskStatusDto request = new UpdateTaskStatusDto();

        Set<ConstraintViolation<UpdateTaskStatusDto>> violations = validator.validate(request);

        assertEquals(1, violations.size());
        assertTrue(violations.stream().anyMatch(v -> "newStatus".equals(v.getPropertyPath().toString())));
    }

    private User saveUser(String email, String username) {
        User user = new User();
        user.setEmail(email);
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode("strongpassword"));
        user.setRole(Role.ROLE_USER);
        return userRepository.save(user);
    }
}
