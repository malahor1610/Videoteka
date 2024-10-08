package com.github.malahor.videoteka.api;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

@Data
public class SearchSeason {

  @JsonAlias("season_number")
  private int number;

  private String name;

  @JsonAlias("episode_count")
  private int episodes;

  @JsonAlias("air_date")
  private String releaseDate;

  public boolean isApplicable(){
    return number > 0 && episodes > 0;
  }
}
