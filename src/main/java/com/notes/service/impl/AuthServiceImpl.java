package com.notes.service.impl;

import com.notes.exception.InvalidTokenException;
import com.notes.exception.ResourceAlreadyExistsException;
import com.notes.jwt.JwtService;
import com.notes.jwt.TokenType;
import com.notes.mail.MailService;
import com.notes.mail.MailType;
import com.notes.model.Role;
import com.notes.model.User;
import com.notes.security.model.AuthRequest;
import com.notes.security.model.AuthResponse;
import com.notes.security.model.ResetRequest;
import com.notes.security.model.RestoreRequest;
import com.notes.service.AuthService;
import com.notes.service.UserService;
import java.util.Properties;
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
  private final MailService mailService;
  private final PasswordEncoder passwordEncoder;
  private final AuthenticationManager authenticationManager;

  @Override
  public void register(final User user) {
    if (userService.existsByUsername(user.getUsername())) {
      throw new ResourceAlreadyExistsException(
          "User with username[%s] already exists.".formatted(user.getUsername()));
    }

    user.getRoles().add(Role.USER);
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    userService.create(user);

    String activationToken = jwtService.generateActivationToken(user.getUsername());

    mailService.sendEmail(
        MailType.ACTIVATION,
        new Properties() {
          {
            put("activationToken", activationToken);
            put("recipientEmail", user.getUsername());
            put("recipientName", user.getName());
          }
        });
  }

  @Override
  public AuthResponse login(AuthRequest authRequest) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            authRequest.getUsername(), authRequest.getPassword()));
    User user = userService.findBy(authRequest.getUsername());

    String accessToken = jwtService.generateAccessToken(authRequest.getUsername());
    String refreshToken = jwtService.generateRefreshToken(authRequest.getUsername());

    mailService.sendEmail(
        MailType.LOGIN,
        new Properties() {
          {
            put("recipientEmail", user.getUsername());
            put("recipientName", user.getName());
          }
        });

    return AuthResponse.builder()
        .userId(user.getId())
        .access(accessToken)
        .refresh(refreshToken)
        .build();
  }

  @Override
  public void reset(final ResetRequest request) {
    if (!jwtService.isValid(request.getToken(), TokenType.RESTORE)) {
      throw new InvalidTokenException();
    }
    String username = jwtService.field(request.getToken(), "subject");
    User user = userService.findBy(username);
    user.setPassword(passwordEncoder.encode(request.getPassword()));
    userService.update(user);
  }

  @Override
  public void restore(final RestoreRequest request) {
    User user = userService.findBy(request.getUsername());

    String restoreToken = jwtService.generateRestoreToken(user.getUsername());

    mailService.sendEmail(
        MailType.RESTORE,
        new Properties() {
          {
            put("restoreToken", restoreToken);
            put("recipientEmail", user.getUsername());
            put("recipientName", user.getName());
          }
        });
  }

  @Override
  public void confirm(final String token) {
    if (jwtService.isValid(token, TokenType.ACTIVATION)) {
      String username = jwtService.field(token, "subject");
      userService.enable(username);
    } else {
      throw new InvalidTokenException();
    }
  }
}
