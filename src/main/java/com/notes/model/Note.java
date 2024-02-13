package com.notes.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Entity
@Table(name = "notes")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Note {
    @Id
    @GeneratedValue(generator = "note_seq")
    @SequenceGenerator(name = "note_seq", sequenceName = "NOTE_SEQ", allocationSize = 1)
    private Long id;
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="user_id", nullable=false)
    public User user;
    @Column(name = "title")
    public String title;
    @Column(name = "body")
    public String body;
    @Column(name = "creation_date")
    public OffsetDateTime creationDate;
}
