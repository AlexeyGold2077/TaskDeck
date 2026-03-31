package com.alexeygold2077.taskdeck.controller;

import com.alexeygold2077.taskdeck.model.dto.UserRegisterRequestDto;
import com.alexeygold2077.taskdeck.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public void register(@Valid @RequestBody UserRegisterRequestDto request) {
        authService.register(request);
    }
}
