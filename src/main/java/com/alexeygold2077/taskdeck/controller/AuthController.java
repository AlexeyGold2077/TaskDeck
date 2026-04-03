package com.alexeygold2077.taskdeck.controller;

import com.alexeygold2077.taskdeck.model.dto.UserLoginRequestDto;
import com.alexeygold2077.taskdeck.model.dto.UserLoginResponseDto;
import com.alexeygold2077.taskdeck.model.dto.UserRegisterRequestDto;
import com.alexeygold2077.taskdeck.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class    AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public void register(@Valid @RequestBody UserRegisterRequestDto request) {
        authService.register(request);
    }

    @PostMapping("/login")
    public UserLoginResponseDto register(@Valid @RequestBody UserLoginRequestDto request) {
        return authService.login(request);
    }
}
