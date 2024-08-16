package com.github.malahor.videoteka.the_movie_db;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.databind.JsonNode;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ShowDetails {

  private long id;

  @JsonAlias({"original_title", "original_name"})
  private String originalTitle;

  @JsonAlias("name")
  private String title;

  private String overview;

  @JsonAlias("poster_path")
  private String poster;

  @JsonAlias({"first_air_date", "release_date"})
  private String releaseDate;

  private SearchType type;

  private List<String> genres;

  @JsonAlias("watch/providers")
  private WatchProviders watchProviders;

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

  public void setGenres(List<JsonNode> genres) {
    this.genres = genres.stream().map(genre -> genre.get("name").asText()).toList();
  }

  @Getter
  private static class WatchProviders {
    private List<String> flatrate;
    private List<String> rent;
    private List<String> buy;

    private static List<String> getList(JsonNode results, String type) {
      return results.get("PL").withArrayProperty(type).findValuesAsText("logo_path").stream()
          .map(logo -> "http://image.tmdb.org/t/p/w92" + logo)
          .toList();
    }

    public void setResults(JsonNode results) {
      this.flatrate = getList(results, "flatrate");
      this.rent = getList(results, "rent");
      this.buy = getList(results, "buy");
    }
  }
}
