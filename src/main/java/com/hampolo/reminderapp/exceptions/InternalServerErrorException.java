package com.hampolo.reminderapp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus (code = HttpStatus.INTERNAL_SERVER_ERROR)
public class InternalServerErrorException extends Exception{

  public InternalServerErrorException(String message) {
    super(message);
  }
}
