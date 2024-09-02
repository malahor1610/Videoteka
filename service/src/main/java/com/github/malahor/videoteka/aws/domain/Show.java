package com.github.malahor.videoteka.aws.domain;

import java.util.Objects;

public class Show {

  private long id;
  private String title;
  private String releaseDate;
  private String poster;
  private double popularity;
  private ShowType showType;

  public Show() {}

  public Show(
      long id,
      String title,
      String releaseDate,
      String poster,
      double popularity,
      ShowType showType) {
    this.id = id;
    this.title = title;
    this.releaseDate = releaseDate;
    this.poster = poster;
    this.popularity = popularity;
    this.showType = showType;
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

  public ShowType getShowType() {
    return showType;
  }

  public void setShowType(ShowType showType) {
    this.showType = showType;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Show show = (Show) o;
    return id == show.id
        && Double.compare(popularity, show.popularity) == 0
        && Objects.equals(title, show.title)
        && Objects.equals(releaseDate, show.releaseDate)
        && Objects.equals(poster, show.poster)
        && showType == show.showType;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, title, releaseDate, poster, popularity, showType);
  }

  @Override
  public String toString() {
    return "Show{"
        + "id="
        + id
        + ", title='"
        + title
        + '\''
        + ", releaseDate='"
        + releaseDate
        + '\''
        + ", poster='"
        + poster
        + '\''
        + ", popularity="
        + popularity
        + ", showType="
        + showType
        + '}';
  }
}
