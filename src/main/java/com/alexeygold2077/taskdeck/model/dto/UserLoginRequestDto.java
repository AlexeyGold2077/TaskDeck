package com.alexeygold2077.taskdeck.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLoginRequestDto {

    @NotBlank
    private String login;

    @NotBlank
    private String password;
}
