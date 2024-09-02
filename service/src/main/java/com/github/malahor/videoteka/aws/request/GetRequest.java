package com.github.malahor.videoteka.aws.request;

import com.github.malahor.videoteka.aws.domain.ShowType;
import java.util.Objects;

public class GetRequest {
  private ShowType type;
  private String userId;

  public GetRequest() {}

  public GetRequest(ShowType type, String userId) {
    this.type = type;
    this.userId = userId;
  }

  public ShowType getType() {
    return type;
  }

  public void setType(ShowType type) {
    this.type = type;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    GetRequest that = (GetRequest) o;
    return type == that.type && Objects.equals(userId, that.userId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(type, userId);
  }

  @Override
  public String toString() {
    return "GetRequest{" +
            "type=" + type +
            ", user='" + userId + '\'' +
            '}';
  }
}
