package com.notes.service;

import com.notes.model.Note;

public interface NoteService {
  Note findBy(Long noteId, Long userId);

  Note findBy(Long noteId);

  Note create(Note note);

  Note update(Note note);
}
