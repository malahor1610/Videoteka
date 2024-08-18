package com.github.malahor.videoteka.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ShowPresentOnWatchlistException extends RuntimeException {

  public ShowPresentOnWatchlistException() {
    super("The show is already on the watchlist");
  }
}
