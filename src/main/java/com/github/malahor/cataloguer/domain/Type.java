package com.github.malahor.cataloguer.domain;

import lombok.Getter;

@Getter
public enum Type {
  MOVIE("movie"),
  MOVIE_SHORT("short"),
  MOVIE_ANIMATED("animated"),
  MOVIE_ANIME("anime"),
  SERIES("series"),
  SERIES_ANIMATED("animated"),
  SERIES_ANIME("anime");

  private final String description;

  Type(String description) {
    this.description = description;
  }
}
