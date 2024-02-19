package com.notes.service.impl;

import com.notes.exception.InvalidTokenException;
import com.notes.exception.ResourceAlreadyExistsException;
import com.notes.jwt.JwtService;
import com.notes.jwt.TokenType;
import com.notes.kafka.producer.KafkaProducer;
import com.notes.mail.model.MailInfoActivation;
import com.notes.mail.model.MailInfoLogin;
import com.notes.mail.model.MailInfoRestore;
import com.notes.model.user.Role;
import com.notes.model.user.User;
import com.notes.security.model.AuthRequest;
import com.notes.security.model.AuthResponse;
import com.notes.security.model.ResetRequest;
import com.notes.security.model.RestoreRequest;
import com.notes.service.AuthService;
import com.notes.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
  private final JwtService jwtService;
  private final UserService userService;
  private final KafkaProducer kafkaProducer;
  private final PasswordEncoder passwordEncoder;
  private final AuthenticationManager authenticationManager;

  @Override
  @Transactional
  public void register(final User user) {
    if (userService.existsByUsername(user.getUsername())) {
      throw new ResourceAlreadyExistsException(
          "User with username[%s] already exists."
              .formatted(user.getUsername()));
    }

    user.getRoles().add(Role.USER);
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    userService.create(user);

    String activationToken =
        jwtService.generateActivationToken(user.getUsername());

    MailInfoActivation activation =
        MailInfoActivation.
            builder().
            recipientName(user.getName()).
            recipientEmail(user.getUsername()).
            activationToken(activationToken).
            build();

    kafkaProducer.produce(activation);
  }

  @Override
  @Transactional(readOnly = true)
  public AuthResponse login(final AuthRequest authRequest) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            authRequest.getUsername(), authRequest.getPassword()));
    User user = userService.findBy(authRequest.getUsername());

    String accessToken =
        jwtService.generateAccessToken(authRequest.getUsername());
    String refreshToken =
        jwtService.generateRefreshToken(authRequest.getUsername());

    MailInfoLogin mailInfo = MailInfoLogin.
        builder().
        recipientName(user.getName()).
        recipientEmail(user.getUsername()).
        build();

    kafkaProducer.produce(mailInfo);

    return AuthResponse.builder()
        .userId(user.getId())
        .access(accessToken)
        .refresh(refreshToken)
        .build();
  }

  @Override
  @Transactional
  public void reset(final ResetRequest request) {
    if (!jwtService.isValid(request.getToken(), TokenType.RESTORE)) {
      throw new InvalidTokenException("Invalid token");
    }
    String username = jwtService.field(request.getToken(), "subject");
    User user = userService.findBy(username);
    user.setPassword(passwordEncoder.encode(request.getPassword()));
    userService.update(user);
  }

  @Override
  @Transactional(readOnly = true)
  public void restore(final RestoreRequest request) {
    User user = userService.findBy(request.getUsername());

    String restoreToken = jwtService.generateRestoreToken(user.getUsername());

    MailInfoRestore restore =
        MailInfoRestore.
            builder().
            recipientName(user.getName()).
            recipientEmail(user.getUsername()).
            restoreToken(restoreToken).
            build();

    kafkaProducer.produce(restore);
  }

  @Override
  @Transactional
  public void confirm(final String token) {
    if (jwtService.isValid(token, TokenType.ACTIVATION)) {
      String username = jwtService.field(token, "subject");
      userService.enable(username);
    } else {
      throw new InvalidTokenException("Invalid token");
    }
  }
}
