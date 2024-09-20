package com.github.malahor.videoteka.exception;

public class ShowsNotFoundException extends RuntimeException {

  public ShowsNotFoundException() {
    super("Brak wyników dla podanego tytułu");
  }
}
