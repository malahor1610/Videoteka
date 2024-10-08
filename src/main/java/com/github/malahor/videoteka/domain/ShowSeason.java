package com.github.malahor.videoteka.domain;

import com.github.malahor.videoteka.api.SearchSeason;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ShowSeason {

  private int number;
  private String name;
  private int episodes;

  public static ShowSeason from(SearchSeason season) {
    return ShowSeason.builder()
        .number(season.getNumber())
        .name(season.getName())
        .episodes(season.getEpisodes())
        .build();
  }
}
