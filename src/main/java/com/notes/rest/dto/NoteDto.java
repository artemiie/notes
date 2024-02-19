package com.notes.rest.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.OffsetDateTime;

public record NoteDto(
    Long id,
    Long userId,
    @NotNull(message = "Note title must be not null.")
    @NotEmpty(message = "Note title must be not empty.")
    String title,
    @NotNull(message = "Note body must be not null.")
    @NotEmpty(message = "Note body must be not empty.")
    String body,
    OffsetDateTime creationDate) {
}
