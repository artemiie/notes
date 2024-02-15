package com.notes.service;

import com.notes.model.user.User;
import com.notes.security.model.AuthRequest;
import com.notes.security.model.AuthResponse;
import com.notes.security.model.ResetRequest;
import com.notes.security.model.RestoreRequest;

public interface AuthService {
  void register(User user);

  AuthResponse login(AuthRequest authRequest);

  void reset(ResetRequest request);

  void restore(RestoreRequest request);

  void confirm(String token);
}
