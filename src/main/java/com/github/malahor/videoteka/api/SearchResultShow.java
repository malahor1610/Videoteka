package com.github.malahor.videoteka.api;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SearchResultShow {

  private long id;

  @JsonAlias("name")
  private String title;

  private String overview;

  @JsonUnwrapped
  private SearchReleaseDate releaseDate;

  @JsonAlias("poster_path")
  private String poster;

  private double popularity;
}
