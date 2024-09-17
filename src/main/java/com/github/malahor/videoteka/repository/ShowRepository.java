package com.github.malahor.videoteka.repository;

import com.github.malahor.videoteka.domain.ShowEntity;
import com.github.malahor.videoteka.domain.ShowType;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.*;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

@Repository
public class ShowRepository {

  private final DynamoDbTable<ShowEntity> showTable;

  public ShowRepository(@Autowired DynamoDbClient dynamoDbClient) {
    var enhancedClient = DynamoDbEnhancedClient.builder().dynamoDbClient(dynamoDbClient).build();
    this.showTable = enhancedClient.table("ShowEntity", TableSchema.fromClass(ShowEntity.class));
  }

  public void save(ShowEntity show) {
    showTable.putItem(show);
  }

  public void update(ShowEntity show) {
    showTable.updateItem(show);
  }

  public void delete(long id, String user) {
    showTable.deleteItem(r -> r.key(k -> k.partitionValue(id).sortValue(user)));
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

  public List<ShowEntity> findByType(ShowType type, String user) {
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
    return showTable.scan(s -> s.consistentRead(true).filterExpression(expression)).items().stream()
        .sorted(Comparator.comparingInt(ShowEntity::getPosition))
        .toList();
  }
}
