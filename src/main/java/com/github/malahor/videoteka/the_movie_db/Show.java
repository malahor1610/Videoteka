package com.github.malahor.videoteka.the_movie_db;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Show {

  private long id;

  @JsonAlias("name")
  private String title;

  private String overview;

  @JsonAlias({"first_air_date", "release_date"})
  private String releaseDate;

  @JsonAlias("poster_path")
  private String poster;

  private double popularity;

  private SearchType type;


  public void setReleaseDate(String releaseDate) {
    if (releaseDate == null || releaseDate.isEmpty()) this.releaseDate = "Unknown";
    else {
      var date = LocalDate.parse(releaseDate, DateTimeFormatter.ISO_DATE);
      this.releaseDate = String.valueOf(date.getYear());
    }
  }

  public void setPoster(String poster) {
    this.poster = "http://image.tmdb.org/t/p/w185" + poster;
  }
}
