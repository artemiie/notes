package com.notes.service.impl;

import com.notes.exception.ResourceNotFoundException;
import com.notes.model.note.Note;
import com.notes.repository.NoteRepository;
import com.notes.service.NoteService;
import java.time.OffsetDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService {
  private final NoteRepository noteRepository;

  @Override
  @Transactional(readOnly = true)
  public Note findBy(Long id, Long userId) {
    return noteRepository
        .findByIdAndUserId(id, userId)
        .orElseThrow(
            () ->
                new ResourceNotFoundException(
                    "Note with id[%s] and userId[%s] not found.".formatted(id, userId)));
  }

  @Override
  @Transactional(readOnly = true)
  public Note findBy(Long id) {
    return noteRepository
        .findById(id)
        .orElseThrow(
            () -> new ResourceNotFoundException("Note with id[%s] not found.".formatted(id)));
  }

  @Override
  @Transactional
  public Note create(Note note) {
    note.setCreationDate(OffsetDateTime.now());
    return noteRepository.save(note);
  }

  @Override
  @Transactional
  public Note update(Note note, Long userId) {
    Note noteOnDb = findBy(note.getId(), userId);
    note.setUser(noteOnDb.getUser());
    note.setCreationDate(noteOnDb.getCreationDate());
    return noteRepository.save(note);
  }

  @Override
  @Transactional
  public void delete(Long noteId) {
    noteRepository.deleteById(noteId);
  }
}
