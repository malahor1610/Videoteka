package com.github.malahor.videoteka.api.tmdb;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Getter;

import java.util.List;

@Getter
class TMDbWatchProviders {
  private List<String> flatrate;
  private List<String> rent;
  private List<String> buy;

  private static List<String> getList(JsonNode results, String type) {
    return results.get("PL").withArrayProperty(type).findValuesAsText("logo_path").stream()
        .map(logo -> "http://image.tmdb.org/t/p/w92" + logo)
        .toList();
  }

  public void setResults(JsonNode results) {
    this.flatrate = getList(results, "flatrate");
    this.rent = getList(results, "rent");
    this.buy = getList(results, "buy");
  }
}
