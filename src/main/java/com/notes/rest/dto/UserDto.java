package com.notes.rest.dto;

import com.notes.model.Role;

import java.util.Set;

public record UserDto(Long id,
                      String name,
                      String username,
                      String password,
                      String passwordConfirmation,
                      Set<Role> roles) {
}