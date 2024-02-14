package com.notes.service.impl;

import com.notes.model.NoteHistory;
import com.notes.repository.NoteHistoryRepository;
import com.notes.service.NoteHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NoteHistoryServiceImpl implements NoteHistoryService {
  private final NoteHistoryRepository noteHistoryRepository;

  @Override
  public void create(NoteHistory noteHistory) {
    noteHistoryRepository.save(noteHistory);
  }
}
