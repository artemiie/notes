package com.notes.exception;

public class ResourceAlreadyExistsException extends RuntimeException {
  public ResourceAlreadyExistsException(final String message) {
    super(message);
  }
}
