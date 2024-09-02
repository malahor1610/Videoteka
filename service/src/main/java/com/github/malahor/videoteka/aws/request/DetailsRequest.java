package com.github.malahor.videoteka.aws.request;

import com.github.malahor.videoteka.aws.domain.ShowType;
import java.util.Objects;

public class DetailsRequest {
  private long id;
  private ShowType type;

  public DetailsRequest() {}

  public DetailsRequest(long id, ShowType type) {
    this.id = id;
    this.type = type;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
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
    DetailsRequest that = (DetailsRequest) o;
    return id == that.id && type == that.type;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, type);
  }

  @Override
  public String toString() {
    return "TMDbSearchRequest{" + "id='" + id + '\'' + ", type=" + type + '}';
  }
}
