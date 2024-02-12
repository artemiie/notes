package com.notes.rest;

import com.notes.model.User;
import com.notes.rest.dto.UserDto;
import com.notes.rest.dto.mapper.UserMapper;
import com.notes.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserMapper userMapper;
    private final AuthService authService;

    @PostMapping
    public void register(@RequestBody final UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        authService.register(user);
    }
}