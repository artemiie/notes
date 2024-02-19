package com.notes.service.impl;

import com.notes.model.note.audit.NoteAudit;
import com.notes.repository.NoteAuditRepository;
import com.notes.service.NoteAuditService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NoteAuditServiceImpl implements NoteAuditService {
  private final NoteAuditRepository noteAuditRepository;

  @Override
  public void create(final NoteAudit noteAudit) {
    noteAuditRepository.save(noteAudit);
  }
}
