package com.alexeygold2077.taskdeck.controller;

import com.alexeygold2077.taskdeck.model.dto.TestRequestDto;
import com.alexeygold2077.taskdeck.model.dto.TestResponseDto;
import com.alexeygold2077.taskdeck.model.dto.UserRegisterRequestDto;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/test")
    public TestResponseDto test(@Valid @RequestBody TestRequestDto request) {
        return new TestResponseDto(
                request.getStr()
        );
    }
}
