package com.github.malahor.videoteka.aws.tmdb_api;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TMDbSearchResult {

  private int page;

  @JsonAlias("total_pages")
  private int totalPages;

  private List<TMDbSearchResultShow> results;

  public TMDbSearchResult() {}

  public TMDbSearchResult(int page, int totalPages, List<TMDbSearchResultShow> results) {
    this.page = page;
    this.totalPages = totalPages;
    this.results = results;
  }

  public int getPage() {
    return page;
  }

  public void setPage(int page) {
    this.page = page;
  }

  public int getTotalPages() {
    return totalPages;
  }

  public void setTotalPages(int totalPages) {
    this.totalPages = totalPages;
  }

  public List<TMDbSearchResultShow> getResults() {
    return results;
  }

  public void setResults(List<TMDbSearchResultShow> results) {
    this.results = results;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    TMDbSearchResult that = (TMDbSearchResult) o;
    return page == that.page
        && totalPages == that.totalPages
        && Objects.equals(results, that.results);
  }

  @Override
  public int hashCode() {
    return Objects.hash(page, totalPages, results);
  }

  @Override
  public String toString() {
    return "TMDbSearchResult{"
        + "page="
        + page
        + ", totalPages="
        + totalPages
        + ", results="
        + results
        + '}';
  }
}
