package com.github.malahor.videoteka.api;

import java.util.List;
import lombok.Data;

@Data
public class SearchResult {

  private List<SearchResultShow> results;
}
