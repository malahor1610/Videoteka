package com.github.malahor.videoteka.exception;

public class ShowPresentOnWatchlistException extends RuntimeException {

  public ShowPresentOnWatchlistException() {
    super("Pozycja jest już na liście");
  }
}
