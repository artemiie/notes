package com.notes.model.note.audit;

import com.notes.model.note.Note;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;

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
  @SequenceGenerator(
      name = "note_audit_seq",
      sequenceName = "NOTE_AUDIT_SEQ",
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
