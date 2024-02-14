package com.notes.service.impl;

import com.notes.exception.ResourceNotFoundException;
import com.notes.model.Note;
import com.notes.repository.NoteRepository;
import com.notes.service.NoteService;
import java.time.OffsetDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService {
  private final NoteRepository noteRepository;

  @Override
  public Note findBy(Long id, Long userId) {
    return noteRepository
        .findByIdAndUserId(id, userId)
        .orElseThrow(
            () ->
                new ResourceNotFoundException(
                    "Note with id[%s] and userId[%s] not found.".formatted(id, userId)));
  }

  @Override
  public Note findBy(Long id) {
    return noteRepository
        .findById(id)
        .orElseThrow(
            () -> new ResourceNotFoundException("Note with id[%s] not found.".formatted(id)));
  }

  @Override
  public Note create(Note note) {
    note.setCreationDate(OffsetDateTime.now());
    return noteRepository.save(note);
  }

  @Override
  public Note update(Note note) {
    return null;
  }
}
