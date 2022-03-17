package com.hampolo.reminderapp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT)
public class DuplicateUserException extends Exception {

  public DuplicateUserException(String message) {
    super(message);
  }
}
