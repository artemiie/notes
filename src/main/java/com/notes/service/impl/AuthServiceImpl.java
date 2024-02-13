package com.notes.service.impl;

import com.notes.exception.ResourceAlreadyExistsException;
import com.notes.model.User;
import com.notes.security.model.AuthRequest;
import com.notes.security.model.AuthResponse;
import com.notes.jwt.JwtService;
import com.notes.service.AuthService;
import com.notes.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final JwtService jwtService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Override
    public void register(final User user) {
        if (userService.existsByUsername(user.getUsername())) {
            throw new ResourceAlreadyExistsException(
                    "User with username[%s] already exists.".formatted(user.getUsername()));
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.create(user);
    }

    @Override
    public AuthResponse login(AuthRequest authRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        User user = userService.findBy(authRequest.getUsername());

        return AuthResponse
                .builder()
                .userId(user.getId())
                .access(jwtService.generateAccessToken(authRequest.getUsername()))
                .refresh(jwtService.generateRefreshToken(authRequest.getUsername())).build();
    }
}