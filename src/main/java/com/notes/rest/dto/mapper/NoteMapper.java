package com.notes.rest.dto.mapper;

import com.notes.model.Note;
import com.notes.rest.dto.NoteDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface NoteMapper extends Mappable<Note, NoteDto> {
    @Mapping(target = "userId", expression = "java(note.getUser().getId())")
    NoteDto toDto(Note note);
}