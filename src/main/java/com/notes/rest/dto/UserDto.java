package com.notes.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record UserDto(
    Long id,
    @NotNull(message = "Username must be not null.")
        @NotEmpty(message = "Username must be not empty.")
        String name,
    @Email(message = "Username must be a valid email.")
        @NotNull(message = "Username must be not null.")
        @NotEmpty(message = "Username must be not empty.")
        String username,
    @NotNull(message = "Username must be not null.")
        @NotEmpty(message = "Username must be not empty.")
        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
        String password,
    @NotNull(message = "Username must be not null.")
        @NotEmpty(message = "Username must be not empty.")
        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
        String passwordConfirmation) {}
