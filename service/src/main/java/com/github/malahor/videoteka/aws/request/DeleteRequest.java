package com.github.malahor.videoteka.aws.request;

import java.util.Objects;

public class DeleteRequest {
  private long id;
  private String userId;

  public DeleteRequest() {}

  public DeleteRequest(long id, String userId) {
    this.id = id;
    this.userId = userId;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
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
    DeleteRequest that = (DeleteRequest) o;
    return id == that.id && Objects.equals(userId, that.userId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, userId);
  }

  @Override
  public String toString() {
    return "DeleteRequest{" +
            "id=" + id +
            ", user='" + userId + '\'' +
            '}';
  }
}
