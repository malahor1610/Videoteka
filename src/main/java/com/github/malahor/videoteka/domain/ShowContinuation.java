package com.github.malahor.videoteka.domain;

import com.github.malahor.videoteka.api.SearchContinuation;
import com.github.malahor.videoteka.api.SearchDetails;
import com.github.malahor.videoteka.api.SearchReleaseDate;
import java.util.Optional;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ShowContinuation {

  private boolean inProduction;
  private String releaseDate;
  private int season;

  public static ShowContinuation from(SearchDetails details, ShowType type) {
    if (ShowType.MOVIE.equals(type)) return null;
    return ShowContinuation.builder()
        .inProduction(details.getInProduction())
        .releaseDate(
            Optional.ofNullable(details.getContinuation())
                .map(SearchContinuation::getReleaseDate)
                .map(SearchReleaseDate::formatted)
                .orElse(null))
        .season(
            Optional.ofNullable(details.getContinuation())
                .map(SearchContinuation::getSeason)
                .filter(season -> season != details.getLastSeason())
                .orElse(0))
        .build();
  }
}
