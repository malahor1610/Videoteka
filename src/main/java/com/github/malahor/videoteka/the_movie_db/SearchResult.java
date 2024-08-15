package com.github.malahor.videoteka.the_movie_db;

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

  @JsonAlias("total_results")
  private int totalResults;

  private List<Show> results;
}
