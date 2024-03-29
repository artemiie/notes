package com.notes.rest;

import com.notes.model.user.User;
import com.notes.rest.dto.UserDto;
import com.notes.rest.dto.mapper.UserMapper;
import com.notes.security.model.AuthRequest;
import com.notes.security.model.AuthResponse;
import com.notes.security.model.ResetRequest;
import com.notes.security.model.RestoreRequest;
import com.notes.service.AuthService;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
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

  @PostMapping("/registration")
  public void register(@RequestBody @Validated final UserDto userDto) {
    User user = userMapper.toEntity(userDto);
    authService.register(user);
  }

  @PostMapping("/login")
  public AuthResponse login(@RequestBody @Validated final AuthRequest request) {
    return authService.login(request);
  }

  @PostMapping("/restore")
  public void restore(@RequestBody final RestoreRequest request) {
    authService.restore(request);
  }

  @PostMapping("/reset")
  public void reset(@RequestBody final ResetRequest request) {
    authService.reset(request);
  }

  @PostMapping("/confirm")
  public void confirm(@RequestBody() @NotEmpty @NotNull final String token) {
    authService.confirm(token);
  }
}
