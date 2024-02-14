package com.notes.rest;

import com.notes.model.Note;
import com.notes.model.User;
import com.notes.rest.dto.NoteDto;
import com.notes.rest.dto.mapper.NoteMapper;
import com.notes.security.SecurityService;
import com.notes.service.NoteService;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/notes")
@RequiredArgsConstructor
public class NoteController {
  private final NoteMapper noteMapper;
  private final NoteService noteService;
  private final SecurityService securityService;

  @GetMapping("/{id}")
  @PreAuthorize("@customSecurityExpresion.canAccessNote(#noteId, #userId)")
  public NoteDto find(
      @PathVariable("id") final String noteId, @RequestHeader("USER_ID") final Long userId) {
    Note note = noteService.findBy(Long.valueOf(noteId), userId);
    return noteMapper.toDto(note);
  }

  @PostMapping()
  public void create(@RequestBody @Validated final NoteDto noteDto) {
    User currentLoggedInUser = securityService.getCurrentLoggedUser();
    Note note = noteMapper.toEntity(noteDto);
    note.setUser(currentLoggedInUser);
    noteService.create(note);
  }

  @PutMapping()
  @PreAuthorize(
      "@customSecurityExpresion.canAccessNote(#noteDto.id, #userId) && "
          + "#userId == authentication.principal.id")
  public NoteDto update(
      @RequestBody @Validated final NoteDto noteDto, @RequestHeader("USER_ID") final Long userId) {
    Note note = noteMapper.toEntity(noteDto);
    note = noteService.update(note, userId);
    return noteMapper.toDto(note);
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("@customSecurityExpresion.canAccessNote(#noteId)")
  public void delete(@PathVariable("id") @NotNull @NotEmpty final String noteId) {
    noteService.delete(Long.valueOf(noteId));
  }
}
