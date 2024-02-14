package com.notes.aop;

import com.notes.model.ActionType;
import com.notes.model.Note;
import com.notes.model.NoteHistory;
import com.notes.service.NoteHistoryService;
import java.time.OffsetDateTime;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class NoteHistoryAspect {
  private final NoteHistoryService noteHistoryService;

  @Pointcut("within(com.notes.service.impl.NoteServiceImpl)")
  public void classPointcut() {}

  @Pointcut("execution(* create(*))")
  public void createMethod() {}

  @Pointcut("execution(* update(*,..))")
  public void updateMethod() {}

  @AfterReturning(value = "classPointcut() && createMethod()", returning = "result")
  public Object createNoteHistory(Object result) {
    NoteHistory noteHistory = buildNoteHistory((Note) result, ActionType.CREATE);
    noteHistoryService.create(noteHistory);
    return result;
  }

  @AfterReturning(value = "classPointcut() && updateMethod()", returning =
          "result")
  public Object updateNoteHistory(Object result) {
    NoteHistory noteHistory = buildNoteHistory((Note) result, ActionType.UPDATE);
    noteHistoryService.create(noteHistory);
    return result;
  }

  private NoteHistory buildNoteHistory(Note note, ActionType actionType) {
    return NoteHistory.builder()
        .note(note)
        .title(note.getTitle())
        .body(note.getBody())
        .modificationDate(OffsetDateTime.now())
        .actionType(actionType)
        .build();
  }
}
