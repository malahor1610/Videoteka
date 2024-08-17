package com.github.malahor.videoteka.api.tmdb;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TMDbSearchResultShow {

  private long id;

  @JsonAlias("name")
  private String title;

  private String overview;

  @JsonAlias({"first_air_date", "release_date"})
  private String releaseDate;

  @JsonAlias("poster_path")
  private String poster;

  private double popularity;

}
