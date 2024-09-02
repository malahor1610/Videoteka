package com.github.malahor.videoteka.aws.exception;

public class ShowPresentOnWatchlistException extends RuntimeException {

  public ShowPresentOnWatchlistException() {
    super("The show is already on the watchlist");
  }
}
