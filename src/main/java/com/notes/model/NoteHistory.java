package com.notes.model;

import jakarta.persistence.*;
import java.time.OffsetDateTime;
import lombok.*;

@Entity
@Table(name = "notes_history")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NoteHistory {
  @Id
  @GeneratedValue(generator = "note_history_seq")
  @SequenceGenerator(
      name = "note_history_seq",
      sequenceName = "NOTE_HISTORY_SEQ",
      allocationSize = 1)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "note_id")
  private Note note;

  @Column(name = "title")
  private String title;

  @Column(name = "body")
  private String body;

  @Column(name = "modification_date")
  private OffsetDateTime modificationDate;

  @Column(name = "action_type")
  @Enumerated(value = EnumType.STRING)
  private ActionType actionType;
}
