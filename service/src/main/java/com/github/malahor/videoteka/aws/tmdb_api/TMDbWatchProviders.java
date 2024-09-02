package com.github.malahor.videoteka.aws.tmdb_api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@JsonIgnoreProperties(ignoreUnknown = true)
class TMDbWatchProviders {
  private List<String> flatrate;
  private List<String> rent;
  private List<String> buy;

  private static List<String> getList(JsonNode results, String type) {
    return Optional.ofNullable(results.get("PL"))
        .map(result -> result.withArrayProperty(type).findValuesAsText("logo_path"))
        .orElse(Collections.emptyList())
        .stream()
        .map(logo -> "http://image.tmdb.org/t/p/w92" + logo)
        .toList();
  }

  public List<String> getFlatrate() {
    return flatrate;
  }

  public List<String> getRent() {
    return rent;
  }

  public List<String> getBuy() {
    return buy;
  }

  public void setResults(JsonNode results) {
    this.flatrate = getList(results, "flatrate");
    this.rent = getList(results, "rent");
    this.buy = getList(results, "buy");
  }
}
