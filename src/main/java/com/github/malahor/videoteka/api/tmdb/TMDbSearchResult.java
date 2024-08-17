package com.github.malahor.videoteka.api.tmdb;

import com.fasterxml.jackson.annotation.JsonAlias;

import java.util.List;

import com.github.malahor.videoteka.api.SearchResult;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TMDbSearchResult implements SearchResult {

  private int page;

  @JsonAlias("total_pages")
  private int totalPages;

  private List<TMDbSearchResultShow> results;

}
