package com.notes.rest.dto;

import com.notes.model.Role;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

public record UserDto(Long id,
                      @NotNull(message = "Username must be not null.")
                      @NotEmpty(message = "Username must be not empty.")
                      String name,
                      @NotNull(message = "Username must be not null.")
                      @NotEmpty(message = "Username must be not empty.")
                      String username,
                      @NotNull(message = "Username must be not null.")
                      @NotEmpty(message = "Username must be not empty.")
                      String password,
                      @NotNull(message = "Username must be not null.")
                      @NotEmpty(message = "Username must be not empty.")
                      String passwordConfirmation,
                      Set<Role> roles) {
}