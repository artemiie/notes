package com.notes.aop;

import com.notes.model.note.Note;
import com.notes.model.note.audit.ActionType;
import com.notes.model.note.audit.NoteAudit;
import com.notes.service.NoteAuditService;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;

@Aspect
@Component
@RequiredArgsConstructor
public class NoteAuditAspect {
  private final NoteAuditService noteAuditService;

  @Pointcut("within(com.notes.service.impl.NoteServiceImpl)")
  public void classPointcut() {
  }

  @Pointcut("execution(* create(*))")
  public void createMethod() {
  }

  @Pointcut("execution(* update(*,..))")
  public void updateMethod() {
  }

  @AfterReturning(
      value = "classPointcut() && createMethod()",
      returning = "result")
  public Object createNoteAudit(final Object result) {
    NoteAudit noteAudit = buildNoteAudit((Note) result, ActionType.CREATE);
    noteAuditService.create(noteAudit);
    return result;
  }

  @AfterReturning(
      value = "classPointcut() && updateMethod()",
      returning = "result")
  public Object updateNoteAudit(final Object result) {
    NoteAudit noteAudit = buildNoteAudit((Note) result, ActionType.UPDATE);
    noteAuditService.create(noteAudit);
    return result;
  }

  private NoteAudit buildNoteAudit(
      final Note note,
      final ActionType actionType) {
    return NoteAudit.builder()
        .note(note)
        .title(note.getTitle())
        .body(note.getBody())
        .modificationDate(OffsetDateTime.now())
        .actionType(actionType)
        .build();
  }
}
