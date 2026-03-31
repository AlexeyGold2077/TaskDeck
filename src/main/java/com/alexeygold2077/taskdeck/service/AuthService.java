package com.alexeygold2077.taskdeck.service;

import com.alexeygold2077.taskdeck.mapper.UserMapper;
import com.alexeygold2077.taskdeck.model.dto.UserRegisterRequestDto;
import com.alexeygold2077.taskdeck.model.entity.Role;
import com.alexeygold2077.taskdeck.model.entity.User;
import com.alexeygold2077.taskdeck.repository.UserRepository;
import org.springframework.stereotype.Service;

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
        user.setRole(Role.USER);
        userRepository.save(user);
    }
}
