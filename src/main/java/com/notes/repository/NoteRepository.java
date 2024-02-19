package com.notes.repository;

import com.notes.model.note.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {
  Optional<Note> findByIdAndUserId(Long id, Long userId);

  void deleteById(Long noteId);
}
