package com.github.malahor.videoteka.api.tmdb;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.malahor.videoteka.api.SearchDetails;
import com.github.malahor.videoteka.domain.ShowType;
import java.util.List;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TMDbSearchDetails implements SearchDetails {

  private long id;

  @JsonAlias({"original_title", "original_name"})
  private String originalTitle;

  @JsonAlias("name")
  private String title;

  private String overview;

  @JsonAlias("release_date")
  private String releaseDate;

  @JsonAlias("number_of_episodes")
  private int runtime;

  private ShowType type;

  private List<String> genres;

  @JsonAlias("watch/providers")
  private TMDbWatchProviders watchProviders;

  public void setGenres(List<JsonNode> genres) {
    this.genres = genres.stream().map(genre -> genre.get("name").asText()).toList();
  }
}
