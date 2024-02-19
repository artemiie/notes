package com.notes.service.impl;

import com.notes.exception.ResourceNotFoundException;
import com.notes.model.note.Note;
import com.notes.repository.NoteRepository;
import com.notes.service.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService {
  private final NoteRepository noteRepository;

  @Override
  @Transactional(readOnly = true)
  public Note findBy(final Long id, final Long userId) {
    return noteRepository
        .findByIdAndUserId(id, userId)
        .orElseThrow(
            () ->
                new ResourceNotFoundException(
                    "Note with id[%s] and userId[%s] not found."
                        .formatted(id, userId)));
  }

  @Override
  @Transactional(readOnly = true)
  public Note findBy(final Long id) {
    return noteRepository
        .findById(id)
        .orElseThrow(
            () -> new ResourceNotFoundException("Note with id[%s] not found."
                .formatted(id)));
  }

  @Override
  @Transactional
  public Note create(final Note note) {
    note.setCreationDate(OffsetDateTime.now());
    return noteRepository.save(note);
  }

  @Override
  @Transactional
  public Note update(final Note note, final Long userId) {
    Note noteOnDb = findBy(note.getId(), userId);
    note.setUser(noteOnDb.getUser());
    note.setCreationDate(noteOnDb.getCreationDate());
    return noteRepository.save(note);
  }

  @Override
  @Transactional
  public void delete(final Long noteId) {
    noteRepository.deleteById(noteId);
  }
}
