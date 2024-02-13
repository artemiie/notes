package com.notes.service;

import com.notes.model.User;

public interface UserService {

    User create(User use);
    User update(User use);

    boolean existsByUsername(String username);

    User findBy(String username);
}