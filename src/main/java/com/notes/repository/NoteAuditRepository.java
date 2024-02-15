package com.notes.repository;

import com.notes.model.note.audit.NoteAudit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteAuditRepository extends JpaRepository<NoteAudit, Long> {}
