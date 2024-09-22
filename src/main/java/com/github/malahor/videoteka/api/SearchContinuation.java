package com.github.malahor.videoteka.api;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchContinuation {

  @JsonAlias("air_date")
  private String releaseDate;

  @JsonAlias("season_number")
  private int season;
}
