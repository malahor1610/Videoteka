package com.github.malahor.videoteka.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

@ApplicationScoped
class DynamoDbConfig {

  @Inject
  @ConfigProperty(name = "quarkus.dynamodb.aws.region")
  private String region;

  public DynamoDbClient dynamoDbClient() {
    return DynamoDbClient.builder().region(Region.of(region)).build();
  }

  public DynamoDbEnhancedClient dynamoDbEnhancedClient(DynamoDbClient dynamoDbClient) {
    return DynamoDbEnhancedClient.builder()
            .dynamoDbClient(dynamoDbClient)
            .build();
  }
}
