package com.github.malahor.videoteka.api;

import com.fasterxml.jackson.annotation.JsonAlias;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SearchResult {

  private int page;

  @JsonAlias("total_pages")
  private int totalPages;

  private List<SearchResultShow> results;
}
