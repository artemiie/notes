package com.notes.model;

import com.notes.model.note.Note;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "note_images")
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Image {

  @Id
  @Column(name = "image_name")
  private String fileName;

  @ManyToOne
  @JoinColumn(name = "note_id")
  private Note note;
}
