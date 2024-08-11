package com.github.malahor.videoteka.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public enum Type {
  @JsonProperty("movie")
  MOVIE,
  @JsonProperty("short movie")
  MOVIE_SHORT,
  @JsonProperty("animated movie")
  MOVIE_ANIMATED,
  @JsonProperty("anime movie")
  MOVIE_ANIME,
  @JsonProperty("series")
  SERIES,
  @JsonProperty("animated series")
  SERIES_ANIMATED,
  @JsonProperty("anime series")
  SERIES_ANIME;
}
