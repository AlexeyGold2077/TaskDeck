package com.alexeygold2077.taskdeck.model.dto;

import com.alexeygold2077.taskdeck.model.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLoginResponseDto {
    User user;
    String token;
}
