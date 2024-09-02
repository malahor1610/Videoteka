package com.github.malahor.videoteka.aws.repository;

import com.github.malahor.videoteka.aws.domain.ShowEntity;
import com.github.malahor.videoteka.aws.domain.ShowType;
import com.github.malahor.videoteka.aws.exception.ShowPresentOnWatchlistException;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import software.amazon.awssdk.enhanced.dynamodb.*;
import software.amazon.awssdk.enhanced.dynamodb.model.*;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

public class ShowRepository {

  private final DynamoDbEnhancedClient dynamoClient;

  public ShowRepository() {
    this.dynamoClient = dynamoClient();
  }

  public void save(ShowEntity show) {
    var showTable = getShowTable();
    handlePosition(show);
    showTable.putItem(show);
  }

  public void updateShow(ShowEntity show) {
    var showTable = getShowTable();
    showTable.updateItem(show);
  }

  public void deleteById(long id, String user) {
    var showTable = getShowTable();
    var key = Key.builder().partitionValue(id).sortValue(user).build();
    showTable.deleteItem(key);
  }

  public List<ShowEntity> findAll(String user) {
    var showTable = getShowTable();
    var expression =
        Expression.builder()
            .expression("userId = :userId")
            .expressionValues(Map.of(":userId", AttributeValue.fromS(user)))
            .build();
    return showTable
        .scan(
            ScanEnhancedRequest.builder().consistentRead(true).filterExpression(expression).build())
        .items()
        .stream()
        .sorted(Comparator.comparingInt(ShowEntity::getPosition))
        .collect(Collectors.toList());
  }

  public List<ShowEntity> findByType(ShowType type, String user) {
    var showTable = getShowTable();
    var expression =
        Expression.builder()
            .expression("showType = :type AND userId = :userId")
            .expressionValues(
                Map.of(
                    ":type",
                    AttributeValue.fromS(type.name()),
                    ":userId",
                    AttributeValue.fromS(user)))
            .build();
    return showTable
        .scan(
            ScanEnhancedRequest.builder().consistentRead(true).filterExpression(expression).build())
        .items()
        .stream()
        .sorted(Comparator.comparingInt(ShowEntity::getPosition))
        .toList();
  }

  private DynamoDbTable<ShowEntity> getShowTable() {
    return dynamoClient.table("ShowEntity", TableSchema.fromClass(ShowEntity.class));
  }

  private void handlePosition(ShowEntity show) throws ShowPresentOnWatchlistException {
    var shows = findAll(show.getUserId());
    if (shows.stream().map(ShowEntity::getId).anyMatch(s -> s.equals(show.getId())))
      throw new ShowPresentOnWatchlistException();
    var maxPosition = shows.isEmpty() ? 0 : shows.getLast().getPosition();
    maxPosition++;
    show.setPosition(maxPosition);
  }

  private DynamoDbEnhancedClient dynamoClient() {
    return DynamoDbEnhancedClient.builder().dynamoDbClient(clientConfig()).build();
  }

  private DynamoDbClient clientConfig() {
    return DynamoDbClient.builder()
        .region(Region.EU_NORTH_1)
        // LOCAL DEV
        // .endpointOverride(URI.create("http://localhost:8000/"))
        .build();
  }
}
