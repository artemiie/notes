package com.notes.service.impl;

import static org.mockito.Mockito.verify;

import com.notes.model.note.audit.NoteAudit;
import com.notes.repository.NoteAuditRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class NoteAuditServiceImplTest {

  @Mock private NoteAuditRepository noteAuditRepository;
  @InjectMocks private NoteAuditServiceImpl noteAuditServiceImpl;

  @Test
  void create() {
    NoteAudit noteAudit = new NoteAudit();

    noteAuditServiceImpl.create(noteAudit);

    verify(noteAuditRepository).save(noteAudit);
  }
}
