package com.notes.service;

import com.notes.model.User;
import com.notes.security.model.AuthRequest;
import com.notes.security.model.AuthResponse;

public interface AuthService {
    void register(User user);

    AuthResponse login(AuthRequest authRequest);
}