package com.github.malahor.videoteka.the_movie_db;

import lombok.Getter;

@Getter
public enum SearchType {
  MOVIE("movie"),
  SERIES("tv");

  private final String apiPath;

  SearchType(String apiPath) {
    this.apiPath = apiPath;
  }
}
