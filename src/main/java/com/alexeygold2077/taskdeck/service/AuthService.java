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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;


    public AuthService(
            UserRepository userRepository,
            UserMapper userMapper,
            JwtService jwtService,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager
    ) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    public void register(UserRegisterRequestDto request) {
        User user = userMapper.toEntity(request);

        if (userRepository.existsByEmail(user.getEmail()))
            throw new ResourceAlreadyExistsException("Email already exists");

        if (userRepository.existsByUsername(user.getUsername()))
            throw new ResourceAlreadyExistsException("Username already exists");

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.ROLE_USER);

        userRepository.save(user);
    }

    public UserLoginResponseDto login(UserLoginRequestDto request) {

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getLogin(),
                            request.getPassword()
                    )
            );

            User user = (User) authentication.getPrincipal();

            String token = jwtService.generateToken(user.getId(), user.getUsername());

            return new UserLoginResponseDto(
                    user.getId(),
                    user.getUsername(),
                    user.getEmail(),
                    token
            );
        } catch (AuthenticationException e) {
            throw new InvalidCredentialsException("Invalid login or password");
        }
    }
}
