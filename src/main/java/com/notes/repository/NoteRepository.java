package com.notes.repository;

import com.notes.model.Note;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteRepository extends JpaRepository<Note, Long> {
  Optional<Note> findByIdAndUserId(Long id, Long userId);

  void deleteById(Long noteId);
}
