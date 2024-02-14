package com.notes.repository;

import com.notes.model.NoteHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteHistoryRepository extends JpaRepository<NoteHistory, Long> {}
