package com.github.malahor.videoteka.domain;

import com.github.malahor.videoteka.api.SearchResultShow;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Show {

  private long id;
  private String title;
  private String releaseDate;
  private String poster;
  private double popularity;
  private ShowType showType;

  public static Show from(SearchResultShow tmdbShow, ShowType type) {
    return Show.builder()
        .id(tmdbShow.getId())
        .title(tmdbShow.getTitle())
        .releaseDate(tmdbShow.getReleaseDate().formatted())
        .poster(tmdbShow.getPoster().full())
        .popularity(tmdbShow.getPopularity())
        .showType(type)
        .build();
  }
}
