package com.github.malahor.cataloguer.domain;

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
