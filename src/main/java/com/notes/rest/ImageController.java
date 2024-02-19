package com.notes.rest;

import com.notes.service.ImageService;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/images")
@RequiredArgsConstructor
public class ImageController {

  private final ImageService imageService;

  @PostMapping(
      value = "/notes/{id}", consumes =
      MediaType.MULTIPART_FORM_DATA_VALUE)
  @PreAuthorize("@customSecurityExpresion.canAccessNote(#noteId)")
  public void uploadImage(
      @PathVariable("id") @NotNull @NotEmpty final String noteId,
      @Validated @RequestBody final MultipartFile file) {
    imageService.upload(noteId, file);
  }
}
