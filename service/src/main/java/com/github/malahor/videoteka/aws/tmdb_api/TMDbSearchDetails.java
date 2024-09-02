package com.github.malahor.videoteka.aws.tmdb_api;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.List;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TMDbSearchDetails {

  private long id;

  @JsonAlias({"original_title", "original_name"})
  private String originalTitle;

  @JsonAlias("name")
  private String title;

  private String overview;

  @JsonAlias({"first_air_date", "release_date"})
  private String releaseDate;

  @JsonAlias("number_of_episodes")
  private int runtime;

  private List<String> genres;

  @JsonAlias("watch/providers")
  private TMDbWatchProviders watchProviders;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getOriginalTitle() {
    return originalTitle;
  }

  public void setOriginalTitle(String originalTitle) {
    this.originalTitle = originalTitle;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getOverview() {
    return overview;
  }

  public void setOverview(String overview) {
    this.overview = overview;
  }

  public String getReleaseDate() {
    return releaseDate;
  }

  public void setReleaseDate(String releaseDate) {
    this.releaseDate = releaseDate;
  }

  public int getRuntime() {
    return runtime;
  }

  public void setRuntime(int runtime) {
    this.runtime = runtime;
  }

  public List<String> getGenres() {
    return genres;
  }

  public void setGenres(List<JsonNode> genres) {
    this.genres = genres.stream().map(genre -> genre.get("name").asText()).toList();
  }

  public TMDbWatchProviders getWatchProviders() {
    return watchProviders;
  }

  public void setWatchProviders(TMDbWatchProviders watchProviders) {
    this.watchProviders = watchProviders;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    TMDbSearchDetails that = (TMDbSearchDetails) o;
    return id == that.id
        && runtime == that.runtime
        && Objects.equals(originalTitle, that.originalTitle)
        && Objects.equals(title, that.title)
        && Objects.equals(overview, that.overview)
        && Objects.equals(releaseDate, that.releaseDate)
        && Objects.equals(genres, that.genres)
        && Objects.equals(watchProviders, that.watchProviders);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        id, originalTitle, title, overview, releaseDate, runtime, genres, watchProviders);
  }

  @Override
  public String toString() {
    return "TMDbSearchDetails{"
        + "id="
        + id
        + ", originalTitle='"
        + originalTitle
        + '\''
        + ", title='"
        + title
        + '\''
        + ", overview='"
        + overview
        + '\''
        + ", releaseDate='"
        + releaseDate
        + '\''
        + ", runtime="
        + runtime
        + ", genres="
        + genres
        + ", watchProviders="
        + watchProviders
        + '}';
  }
}
