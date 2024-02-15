package com.notes.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.notes.exception.ResourceNotFoundException;
import com.notes.model.note.Note;
import com.notes.model.user.User;
import com.notes.repository.NoteRepository;
import java.time.OffsetDateTime;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class NoteServiceImplTest {

  @Mock private NoteRepository noteRepository;

  @InjectMocks private NoteServiceImpl noteServiceImpl;

  @Test
  void findBy() {
    Note expectedResult = new Note();

    doReturn(Optional.of(expectedResult)).when(noteRepository).findByIdAndUserId(any(), any());

    var actualResult = noteServiceImpl.findBy(any(), any());

    assertEquals(expectedResult, actualResult);

    verify(noteRepository).findByIdAndUserId(any(), any());
  }

  @Test
  void findBy_withNotExistingId() {
    doReturn(Optional.empty()).when(noteRepository).findByIdAndUserId(any(), any());

    assertThrows(ResourceNotFoundException.class, () -> noteServiceImpl.findBy(any(), any()));

    verify(noteRepository).findByIdAndUserId(any(), any());
  }

  @Test
  void testFindBy() {
    Note expectedResult = new Note();

    doReturn(Optional.of(expectedResult)).when(noteRepository).findById(any());

    var actualResult = noteServiceImpl.findBy(any());

    assertEquals(expectedResult, actualResult);

    verify(noteRepository).findById(any());
  }

  @Test
  void testFindBy_withNotExistingId() {
    doReturn(Optional.empty()).when(noteRepository).findById(any());

    assertThrows(ResourceNotFoundException.class, () -> noteServiceImpl.findBy(any()));

    verify(noteRepository).findById(any());
  }

  @Test
  void create() {
    Note expectedResult = new Note();

    doAnswer(
            invocationOnMock -> {
              Note note = invocationOnMock.getArgument(0);
              note.setId(1L);
              return note;
            })
        .when(noteRepository)
        .save(expectedResult);

    var actualResult = noteServiceImpl.create(expectedResult);

    assertAll(
        () -> assertNotNull(actualResult),
        () -> assertEquals(expectedResult.getId(), actualResult.getId()),
        () -> assertEquals(expectedResult.getTitle(), actualResult.getTitle()),
        () -> assertEquals(expectedResult.getBody(), actualResult.getBody()),
        () -> assertEquals(expectedResult.getUser(), actualResult.getUser()),
        () -> assertNotNull(actualResult.getCreationDate()));

    verify(noteRepository).save(expectedResult);
  }

  @Test
  void update() {
    when(noteRepository.save(any())).thenAnswer(i -> i.getArguments()[0]);

    Note noteOndb = new Note();
    noteOndb.setId(1L);
    noteOndb.setTitle("Old body");
    noteOndb.setBody("Old body");
    noteOndb.setCreationDate(OffsetDateTime.now());

    doReturn(Optional.of(noteOndb)).when(noteRepository).findByIdAndUserId(any(), any());

    Note expectedResult = new Note();
    expectedResult.setId(1L);
    expectedResult.setTitle("New body");
    expectedResult.setBody("New body");
    expectedResult.setUser(new User());
    expectedResult.setCreationDate(OffsetDateTime.now());

    var actualResult = noteServiceImpl.update(expectedResult, 1L);

    assertAll(
        () -> assertNotNull(actualResult),
        () -> assertEquals(expectedResult.getId(), actualResult.getId()),
        () -> assertEquals(expectedResult.getTitle(), actualResult.getTitle()),
        () -> assertEquals(expectedResult.getBody(), actualResult.getBody()),
        () -> assertEquals(expectedResult.getUser(), actualResult.getUser()),
        () -> assertEquals(expectedResult.getCreationDate(), actualResult.getCreationDate()));

    verify(noteRepository).save(expectedResult);
    verify(noteRepository).findByIdAndUserId(1L, 1L);
  }

  @Test
  void delete() {
    noteServiceImpl.delete(any());

    verify(noteRepository).deleteById(any());
  }
}
