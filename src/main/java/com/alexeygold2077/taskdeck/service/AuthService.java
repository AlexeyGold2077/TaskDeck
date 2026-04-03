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
    private final JwtService jwtService;

    public AuthService(
            UserRepository userRepository,
            UserMapper userMapper,
            JwtService jwtService
    ) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.jwtService = jwtService;
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

        User user = userRepository.findByEmail(login);

        if (user == null) {
            user = userRepository.findByUsername(login);
        }

        if (user == null || !Objects.equals(user.getPassword(), password)) {
            throw new InvalidCredentialsException("Invalid credentials");
        }

        String token = jwtService.generateToken(user.getUsername());

        UserLoginResponseDto response = new UserLoginResponseDto();
        response.setUser(user);
        response.setToken(token);

        return response;
    }
}
