package com.github.malahor.videoteka.domain;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

@Getter
@Setter
@DynamoDbBean
public class ShowEntity {

  @Getter(onMethod_ = {@DynamoDbPartitionKey})
  private long id;

  @Getter(onMethod_ = {@DynamoDbSortKey})
  private String userId;

  private String title;
  private String originalTitle;
  private String releaseDate;
  private String poster;
  private String duration;
  private ShowType showType;
  private ShowLockState lockState;
  private int position;
  private List<String> genres;
  private ShowWatchState watchState;

  public static ShowEntity from(ShowDetails details, String poster, String user) {
    var entity = new ShowEntity();
    entity.setId(details.getId());
    entity.setTitle(details.getTitle());
    entity.setOriginalTitle(details.getOriginalTitle());
    entity.setReleaseDate(details.getReleaseDate());
    entity.setPoster(poster);
    entity.setDuration(details.getDuration());
    entity.setShowType(details.getShowType());
    entity.setGenres(details.getGenres());
    entity.setUserId(user);
    return entity;
  }

  public ShowEntity withUser(String user) {
    this.userId = user;
    return this;
  }

  public ShowEntity withMaxPositionOf(List<ShowEntity> shows) {
    var maxPosition = shows.isEmpty() ? 0 : shows.getLast().getPosition();
    maxPosition++;
    this.position = maxPosition;
    return this;
  }

  public ShowEntity withPosition(int position) {
    this.position = position;
    return this;
  }

  public boolean isWatchedOnList() {
    return ShowWatchState.WATCHED_ON_LIST.equals(watchState);
  }

  public boolean isWatchedNotOnList() {
    return ShowWatchState.WATCHED.equals(watchState);
  }

  public ShowEntity withWatchState(ShowWatchState watchState) {
    this.watchState = watchState;
    return this;
  }

  public ShowEntity withLockState(ShowLockState lockState) {
    this.lockState = lockState;
    return this;
  }

  public void updateWithNewData(ShowDetails details, String poster) {
    this.title = details.getTitle();
    this.originalTitle = details.getOriginalTitle();
    this.releaseDate = details.getReleaseDate();
    this.poster = poster;
    this.duration = details.getDuration();
    if (ShowType.SERIES.equals(showType))
      this.lockState = ShowLockState.lockByDetails(details).changed();
    this.genres = details.getGenres();
  }
}
