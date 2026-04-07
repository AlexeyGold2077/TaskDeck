package com.alexeygold2077.taskdeck.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CreateProjectRequestDto {
    private String name;
    private String description;
    private Integer createdAt;
}
