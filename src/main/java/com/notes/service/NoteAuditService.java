package com.notes.service;

import com.notes.model.note.audit.NoteAudit;

public interface NoteAuditService {
  void create(NoteAudit noteAudit);
}
