package com.notes.model.note.audit;

import com.notes.model.note.Note;
import jakarta.persistence.*;
import java.time.OffsetDateTime;
import lombok.*;

@Entity
@Table(name = "notes_audit")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NoteAudit {
  @Id
  @GeneratedValue(generator = "note_audit_seq")
  @SequenceGenerator(name = "note_audit_seq", sequenceName = "NOTE_AUDIT_SEQ", allocationSize = 1)
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
