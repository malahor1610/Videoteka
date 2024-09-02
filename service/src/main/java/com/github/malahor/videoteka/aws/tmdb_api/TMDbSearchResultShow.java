package com.github.malahor.videoteka.aws.tmdb_api;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
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

  public TMDbSearchResultShow() {}

  public TMDbSearchResultShow(
      long id,
      String title,
      String overview,
      String releaseDate,
      String poster,
      double popularity) {
    this.id = id;
    this.title = title;
    this.overview = overview;
    this.releaseDate = releaseDate;
    this.poster = poster;
    this.popularity = popularity;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
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

  public String getPoster() {
    return poster;
  }

  public void setPoster(String poster) {
    this.poster = poster;
  }

  public double getPopularity() {
    return popularity;
  }

  public void setPopularity(double popularity) {
    this.popularity = popularity;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    TMDbSearchResultShow that = (TMDbSearchResultShow) o;
    return id == that.id
        && Double.compare(popularity, that.popularity) == 0
        && Objects.equals(title, that.title)
        && Objects.equals(overview, that.overview)
        && Objects.equals(releaseDate, that.releaseDate)
        && Objects.equals(poster, that.poster);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, title, overview, releaseDate, poster, popularity);
  }

  @Override
  public String toString() {
    return "TMDbSearchResultShow{"
        + "id="
        + id
        + ", title='"
        + title
        + '\''
        + ", overview='"
        + overview
        + '\''
        + ", releaseDate='"
        + releaseDate
        + '\''
        + ", poster='"
        + poster
        + '\''
        + ", popularity="
        + popularity
        + '}';
  }
}
