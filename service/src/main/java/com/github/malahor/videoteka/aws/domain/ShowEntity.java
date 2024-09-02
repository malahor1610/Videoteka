package com.github.malahor.videoteka.aws.domain;

import java.util.Objects;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

@DynamoDbBean
public class ShowEntity {

  private long id;
  private String userId;
  private String title;
  private String originalTitle;
  private String releaseDate;
  private String poster;
  private String duration;
  private ShowType showType;
  private int position;

  public ShowEntity() {}

  public ShowEntity(
      long id,
      String userId,
      String title,
      String originalTitle,
      String releaseDate,
      String poster,
      String duration,
      ShowType showType,
      int position) {
    this.id = id;
    this.userId = userId;
    this.title = title;
    this.originalTitle = originalTitle;
    this.releaseDate = releaseDate;
    this.poster = poster;
    this.duration = duration;
    this.showType = showType;
    this.position = position;
  }

  @DynamoDbPartitionKey
  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  @DynamoDbSortKey
  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getOriginalTitle() {
    return originalTitle;
  }

  public void setOriginalTitle(String originalTitle) {
    this.originalTitle = originalTitle;
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

  public int getPosition() {
    return position;
  }

  public void setPosition(int position) {
    this.position = position;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ShowEntity that = (ShowEntity) o;
    return id == that.id
        && position == that.position
        && Objects.equals(userId, that.userId)
        && Objects.equals(title, that.title)
        && Objects.equals(originalTitle, that.originalTitle)
        && Objects.equals(releaseDate, that.releaseDate)
        && Objects.equals(poster, that.poster)
        && Objects.equals(duration, that.duration)
        && showType == that.showType;
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        id, userId, title, originalTitle, releaseDate, poster, duration, showType, position);
  }

  @Override
  public String toString() {
    return "ShowEntity{"
        + "id="
        + id
        + ", user='"
        + userId
        + '\''
        + ", title='"
        + title
        + '\''
        + ", originalTitle='"
        + originalTitle
        + '\''
        + ", releaseDate='"
        + releaseDate
        + '\''
        + ", poster='"
        + poster
        + '\''
        + ", duration='"
        + duration
        + '\''
        + ", showType="
        + showType
        + ", position="
        + position
        + '}';
  }
}
