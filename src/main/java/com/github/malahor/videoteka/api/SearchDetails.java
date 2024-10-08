package com.github.malahor.videoteka.api;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.List;
import lombok.*;

@Data
public class SearchDetails {

  private long id;

  @JsonAlias({"original_title", "original_name"})
  private String originalTitle;

  @JsonAlias("name")
  private String title;

  private String overview;

  @JsonUnwrapped private SearchReleaseDate releaseDate;

  @JsonAlias("in_production")
  private Boolean inProduction;

  @JsonAlias("next_episode_to_air")
  private SearchContinuation continuation;

  @JsonAlias("number_of_episodes")
  private int runtime;

  private List<String> genres;

  @JsonAlias("watch/providers")
  private SearchWatchProviders watchProviders;

  @JsonAlias("belongs_to_collection")
  private SearchCollection collection;

  private List<SearchSeason> seasons;

  public void setGenres(List<JsonNode> genres) {
    this.genres = genres.stream().map(genre -> genre.get("name").asText()).toList();
  }
}
