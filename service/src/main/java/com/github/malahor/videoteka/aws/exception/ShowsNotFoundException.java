package com.github.malahor.videoteka.aws.exception;

public class ShowsNotFoundException extends RuntimeException {

  public ShowsNotFoundException() {
    super("No shows found for given title");
  }
}
