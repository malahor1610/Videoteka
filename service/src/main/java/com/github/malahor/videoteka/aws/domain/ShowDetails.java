package com.github.malahor.videoteka.aws.domain;

import java.util.List;
import java.util.Objects;

public class ShowDetails {
  private long id;
  private String originalTitle;
  private String title;
  private String overview;
  private String releaseDate;
  private String duration;
  private ShowType showType;
  private List<String> genres;
  private WatchProviders watchProviders;

  public ShowDetails() {}

  public ShowDetails(
      long id,
      String originalTitle,
      String title,
      String overview,
      String releaseDate,
      String duration,
      ShowType showType,
      List<String> genres,
      WatchProviders watchProviders) {
    this.id = id;
    this.originalTitle = originalTitle;
    this.title = title;
    this.overview = overview;
    this.releaseDate = releaseDate;
    this.duration = duration;
    this.showType = showType;
    this.genres = genres;
    this.watchProviders = watchProviders;
  }

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

  public String getDuration() {
    return duration;
  }

  public void setDuration(String duration) {
    this.duration = duration;
  }

  public ShowType getShowType() {
    return showType;
  }

  public void setShowType(ShowType showType) {
    this.showType = showType;
  }

  public List<String> getGenres() {
    return genres;
  }

  public void setGenres(List<String> genres) {
    this.genres = genres;
  }

  public WatchProviders getWatchProviders() {
    return watchProviders;
  }

  public void setWatchProviders(WatchProviders watchProviders) {
    this.watchProviders = watchProviders;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ShowDetails that = (ShowDetails) o;
    return id == that.id
        && Objects.equals(originalTitle, that.originalTitle)
        && Objects.equals(title, that.title)
        && Objects.equals(overview, that.overview)
        && Objects.equals(releaseDate, that.releaseDate)
        && Objects.equals(duration, that.duration)
        && showType == that.showType
        && Objects.equals(genres, that.genres)
        && Objects.equals(watchProviders, that.watchProviders);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        id,
        originalTitle,
        title,
        overview,
        releaseDate,
        duration,
        showType,
        genres,
        watchProviders);
  }

  @Override
  public String toString() {
    return "ShowDetails{"
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
        + ", duration='"
        + duration
        + '\''
        + ", showType="
        + showType
        + ", genres="
        + genres
        + ", watchProviders="
        + watchProviders
        + '}';
  }
}
