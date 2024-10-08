package com.github.malahor.videoteka.api;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Data;

@Data
public class SearchContinuation {

  @JsonUnwrapped private SearchReleaseDate releaseDate;

  @JsonAlias("season_number")
  private int season;
}
