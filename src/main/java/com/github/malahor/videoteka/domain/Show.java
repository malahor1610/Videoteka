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

  public static Show from(SearchResultShow show, ShowType type) {
    return Show.builder()
        .id(show.getId())
        .title(show.getTitle())
        .releaseDate(show.getReleaseDate().formatted())
        .poster(show.getPoster().full())
        .popularity(show.getPopularity())
        .showType(type)
        .build();
  }
}
