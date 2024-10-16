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

  public static ShowEntity from(ShowDetails details, String poster) {
    var entity = new ShowEntity();
    entity.setId(details.getId());
    entity.setTitle(details.getTitle());
    entity.setOriginalTitle(details.getOriginalTitle());
    entity.setReleaseDate(details.getReleaseDate());
    entity.setPoster(poster);
    entity.setDuration(details.getDuration());
    entity.setShowType(details.getShowType());
    entity.setGenres(details.getGenres());
    return entity;
  }
}
