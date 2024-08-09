package com.github.malahor.videoteka.domain;

import lombok.Getter;

@Getter
public enum Platform {
  NETFLIX("Netflix"),
  MAX("Max"),
  DISNEY("Disney+"),
  PRIME("Amazon Prime");

  private final String description;

  Platform(String description) {
    this.description = description;
  }
}
