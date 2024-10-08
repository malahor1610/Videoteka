package com.github.malahor.videoteka.api;

import com.fasterxml.jackson.databind.JsonNode;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import lombok.Getter;

@Getter
public class SearchWatchProviders {
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

  public void setResults(JsonNode results) {
    this.flatrate = getList(results, "flatrate");
    this.rent = getList(results, "rent");
    this.buy = getList(results, "buy");
  }
}
