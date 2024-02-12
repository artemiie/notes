package com.notes.service.impl;

import com.notes.exception.ResourceAlreadyExistsException;
import com.notes.model.User;
import com.notes.service.AuthService;
import com.notes.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void register(final User user) {
        if (userService.existsByUsername(user.getUsername())) {
            throw new ResourceAlreadyExistsException(
                    "User with username[%s] already exists.".formatted(user.getUsername()));
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.create(user);
    }
}