package com.hampolo.reminderapp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT)
public class DuplicateDataException extends Exception{

  public DuplicateDataException(String message) {
    super(message);
  }
}
