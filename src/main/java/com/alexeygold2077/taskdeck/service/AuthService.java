package com.alexeygold2077.taskdeck.service;

import com.alexeygold2077.taskdeck.exception.InvalidCredentialsException;
import com.alexeygold2077.taskdeck.exception.ResourceAlreadyExistsException;
import com.alexeygold2077.taskdeck.mapper.UserMapper;
import com.alexeygold2077.taskdeck.model.dto.UserLoginRequestDto;
import com.alexeygold2077.taskdeck.model.dto.UserLoginResponseDto;
import com.alexeygold2077.taskdeck.model.dto.UserRegisterRequestDto;
import com.alexeygold2077.taskdeck.model.entity.Role;
import com.alexeygold2077.taskdeck.model.entity.User;
import com.alexeygold2077.taskdeck.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public AuthService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public void register(UserRegisterRequestDto request) {
        User user = userMapper.toEntity(request);

        if (userRepository.existsByEmail(user.getEmail()))
            throw new ResourceAlreadyExistsException("Email already exists");
        if (userRepository.existsByUsername(user.getUsername()))
            throw new ResourceAlreadyExistsException("Username already exists");

        user.setRole(Role.USER);

        userRepository.save(user);
    }

    public UserLoginResponseDto login(UserLoginRequestDto request) {

        String login = request.getLogin();
        String password = request.getPassword();

        UserLoginResponseDto response = new UserLoginResponseDto();

        User userByEmail = userRepository.findByEmail(login);
        if (userByEmail != null && Objects.equals(userByEmail.getPassword(), password)) {
            response.setUser(userByEmail);
            return response;
        }

        User userByUsername = userRepository.findByUsername(login);
        if (userByUsername != null && Objects.equals(userByUsername.getPassword(), password)) {
            response.setUser(userByUsername);
            return response;
        }

        throw new InvalidCredentialsException("Invalid email or password");
    }
}
