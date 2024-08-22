package com.github.malahor.videoteka.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ShowsNotFoundException extends RuntimeException {

  public ShowsNotFoundException() {
    super("No shows found for given title");
  }
}
