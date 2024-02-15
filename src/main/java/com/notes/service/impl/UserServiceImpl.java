package com.notes.service.impl;

import com.notes.exception.ResourceNotFoundException;
import com.notes.model.user.User;
import com.notes.repository.UserRepository;
import com.notes.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;

  @Override
  public User create(final User user) {
    return userRepository.save(user);
  }

  @Override
  public User update(User user) {
    User userOnDb = findBy(user.getUsername());
    user.setId(userOnDb.getId());
    return userRepository.save(user);
  }

  @Override
  public boolean existsByUsername(final String username) {
    return userRepository.existsByUsername(username);
  }

  @Override
  public User findBy(String username) {
    return userRepository
        .findByUsername(username)
        .orElseThrow(
            () ->
                new ResourceNotFoundException(
                    "User with username[%s] not found.".formatted(username)));
  }

  @Override
  public boolean isNoteOwner(Long userId, Long noteId) {
    return userRepository.isNoteOwner(userId, noteId);
  }

  @Override
  public void enable(final String username) {
    User user = findBy(username);
    user.setEnabled(true);
    update(user);
  }
}
