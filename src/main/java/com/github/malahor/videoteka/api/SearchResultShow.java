package com.github.malahor.videoteka.api;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Data;

@Data
public class SearchResultShow {

  private long id;

  @JsonAlias("name")
  private String title;

  private String overview;

  @JsonUnwrapped private SearchReleaseDate releaseDate;

  @JsonUnwrapped private SearchPoster poster;

  private double popularity;
}
