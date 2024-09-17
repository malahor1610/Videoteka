package com.github.malahor.videoteka.domain;

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
  private int position;
}
