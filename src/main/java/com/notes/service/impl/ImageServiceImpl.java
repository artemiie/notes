package com.notes.service.impl;

import com.notes.model.Image;
import com.notes.model.note.Note;
import com.notes.repository.ImageRepository;
import com.notes.service.ImageService;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
  private final ImageRepository imageRepository;

  @Override
  public void upload(@NotNull @NotEmpty String noteId, MultipartFile file) {
    Note note = new Note();
    note.setId(Long.valueOf(noteId));

    Image image = new Image();
    image.setNote(note);
    image.setFileName(file.getOriginalFilename());

    imageRepository.save(image);
  }
}
