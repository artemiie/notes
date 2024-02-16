package com.notes.service;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

  void upload(@NotNull @NotEmpty String noteId, MultipartFile image);
}
