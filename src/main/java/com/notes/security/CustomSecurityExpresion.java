package com.notes.security;

import com.notes.security.model.CustomUserDetails;
import com.notes.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service("customSecurityExpresion")
@RequiredArgsConstructor
public class CustomSecurityExpresion {
  private final UserService userService;

  public boolean canAccessNote(String noteId, Long userId) {
    return userService.isNoteOwner(userId, Long.valueOf(noteId));
  }

  public boolean canAccessNote(String noteId) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
    Long userId = user.getId();
    return userService.isNoteOwner(userId, Long.valueOf(noteId));
  }
}
