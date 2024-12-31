package com.github.malahor.videoteka.repository;

import com.github.malahor.videoteka.domain.ShowEntity;
import com.github.malahor.videoteka.domain.ShowType;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import software.amazon.awssdk.enhanced.dynamodb.*;
import software.amazon.awssdk.enhanced.dynamodb.model.TransactUpdateItemEnhancedRequest;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

@ApplicationScoped
public class ShowRepository {

  private final DynamoDbEnhancedClient client;
  private final DynamoDbTable<ShowEntity> showTable;

  @Inject
  public ShowRepository(DynamoDbEnhancedClient dynamoDbEnhancedClient) {
    this.client = dynamoDbEnhancedClient;
    this.showTable =
        dynamoDbEnhancedClient.table("ShowEntity", TableSchema.fromClass(ShowEntity.class));
  }

  public void save(ShowEntity show) {
    showTable.putItem(show);
  }

  public void update(ShowEntity show) {
    showTable.updateItem(show);
  }

  public void update(List<ShowEntity> shows) {
    client.transactWriteItems(
        b ->
            shows.forEach(
                show ->
                    b.addUpdateItem(
                        showTable,
                        TransactUpdateItemEnhancedRequest.builder(ShowEntity.class)
                            .item(show)
                            .build())));
  }

  public void delete(long id, String user) {
    showTable.deleteItem(r -> r.key(k -> k.partitionValue(id).sortValue(user)));
  }

  public List<ShowEntity> findAll() {
    return showTable.scan(s -> s.consistentRead(true)).items().stream()
        .collect(Collectors.toList());
  }

  public List<ShowEntity> findAll(String user) {
    var expression =
        Expression.builder()
            .expression("userId = :userId")
            .expressionValues(Map.of(":userId", AttributeValue.fromS(user)))
            .build();
    return showTable.scan(s -> s.consistentRead(true).filterExpression(expression)).items().stream()
        .sorted(Comparator.comparingInt(ShowEntity::getPosition))
        .collect(Collectors.toList());
  }

  public List<ShowEntity> findWatchlist(String user) {
    var expression =
        Expression.builder()
            .expression("NOT watchState = :watched AND userId = :userId")
            .expressionValues(
                Map.of(
                    ":watched",
                    AttributeValue.fromS("WATCHED"),
                    ":userId",
                    AttributeValue.fromS(user)))
            .build();
    return showTable.scan(s -> s.consistentRead(true).filterExpression(expression)).items().stream()
        .sorted(Comparator.comparingInt(ShowEntity::getPosition))
        .collect(Collectors.toList());
  }

  public List<ShowEntity> findByType(ShowType type, String user) {
    var expression =
        Expression.builder()
            .expression("NOT watchState = :watched AND showType = :type AND userId = :userId")
            .expressionValues(
                Map.of(
                    ":watched",
                    AttributeValue.fromS("WATCHED"),
                    ":type",
                    AttributeValue.fromS(type.name()),
                    ":userId",
                    AttributeValue.fromS(user)))
            .build();
    return showTable.scan(s -> s.consistentRead(true).filterExpression(expression)).items().stream()
        .sorted(Comparator.comparingInt(ShowEntity::getPosition))
        .toList();
  }

  public List<ShowEntity> findReleasedByType(ShowType type, String user) {
    var expression =
        Expression.builder()
            .expression(
                "NOT watchState = :watched AND showType = :type AND size(releaseDate) = :yearSize AND userId = :userId")
            .expressionValues(
                Map.of(
                    ":watched",
                    AttributeValue.fromS("WATCHED"),
                    ":type",
                    AttributeValue.fromS(type.name()),
                    ":yearSize",
                    AttributeValue.fromN("4"),
                    ":userId",
                    AttributeValue.fromS(user)))
            .build();
    return showTable.scan(s -> s.consistentRead(true).filterExpression(expression)).items().stream()
        .sorted(Comparator.comparingInt(ShowEntity::getPosition))
        .toList();
  }

  public ShowEntity findById(long id, String user) {
    return showTable.getItem(r -> r.key(k -> k.partitionValue(id).sortValue(user)));
  }

  public List<ShowEntity> findLocked() {
    var expression =
        Expression.builder()
            .expression("begins_with(lockState, :locked) AND NOT contains(showStatus, :changed)")
            .expressionValues(
                Map.of(
                    ":locked",
                    AttributeValue.fromS("LOCKED"),
                    ":changed",
                    AttributeValue.fromS("CHANGED")))
            .build();
    return showTable.scan(s -> s.consistentRead(true).filterExpression(expression)).items().stream()
        .sorted(Comparator.comparingInt(ShowEntity::getPosition))
        .collect(Collectors.toList());
  }

  public List<ShowEntity> findWatchedByType(ShowType type, String user) {
    var expression =
        Expression.builder()
            .expression(
                "begins_with(watchState, :watched) AND showType = :type AND userId = :userId")
            .expressionValues(
                Map.of(
                    ":watched",
                    AttributeValue.fromS("WATCHED"),
                    ":type",
                    AttributeValue.fromS(type.name()),
                    ":userId",
                    AttributeValue.fromS(user)))
            .build();
    return showTable.scan(s -> s.consistentRead(true).filterExpression(expression)).items().stream()
        .sorted(Comparator.comparing(ShowEntity::getTitle))
        .toList();
  }
}
