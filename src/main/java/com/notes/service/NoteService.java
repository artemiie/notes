package com.notes.service;


import com.notes.model.note.Note;

public interface NoteService {
  Note findBy(Long noteId, Long userId);

  Note findBy(Long noteId);

  Note create(Note note);

  Note update(Note note, Long userId);

  void delete(Long noteId);
}
