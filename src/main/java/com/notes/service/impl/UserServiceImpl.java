package com.notes.service.impl;

import com.notes.exception.ResourceNotFoundException;
import com.notes.model.user.User;
import com.notes.repository.UserRepository;
import com.notes.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;

  @Override
  @Transactional
  public User create(final User user) {
    return userRepository.save(user);
  }

  @Override
  @Transactional
  public User update(final User user) {
    User userOnDb = findBy(user.getUsername());
    user.setId(userOnDb.getId());
    return userRepository.save(user);
  }

  @Override
  @Transactional(readOnly = true)
  public boolean existsByUsername(final String username) {
    return userRepository.existsByUsername(username);
  }

  @Override
  @Transactional(readOnly = true)
  public User findBy(final String username) {
    return userRepository
        .findByUsername(username)
        .orElseThrow(
            () ->
                new ResourceNotFoundException(
                    "User with username[%s] not found.".formatted(username)));
  }

  @Override
  @Transactional(readOnly = true)
  public boolean isNoteOwner(final Long userId, final Long noteId) {
    return userRepository.isNoteOwner(userId, noteId);
  }

  @Override
  @Transactional
  public void enable(final String username) {
    User user = findBy(username);
    user.setEnabled(true);
    update(user);
  }
}
