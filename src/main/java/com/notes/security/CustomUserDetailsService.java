package com.notes.security;

import com.notes.model.user.User;
import com.notes.security.model.CustomUserDetails;
import com.notes.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor()
public class CustomUserDetailsService implements UserDetailsService {

  private final UserService userService;

  @Override
  public CustomUserDetails loadUserByUsername(final String username) {
    User user = userService.findBy(username);
    return new CustomUserDetails(user);
  }
}
