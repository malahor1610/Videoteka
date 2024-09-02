package com.github.malahor.videoteka.aws.request;

import com.github.malahor.videoteka.aws.domain.ShowType;

import java.util.Objects;

public class SearchRequest {
  private String title;
  private ShowType type;

  public SearchRequest() {}

  public SearchRequest(String title, ShowType type) {
    this.title = title;
    this.type = type;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public ShowType getType() {
    return type;
  }

  public void setType(ShowType type) {
    this.type = type;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    SearchRequest that = (SearchRequest) o;
    return Objects.equals(title, that.title) && type == that.type;
  }

  @Override
  public int hashCode() {
    return Objects.hash(title, type);
  }

  @Override
  public String toString() {
    return "TMDbSearchRequest{" + "title='" + title + '\'' + ", type=" + type + '}';
  }
}
