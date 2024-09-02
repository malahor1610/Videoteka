package com.github.malahor.videoteka.aws.request;

import com.github.malahor.videoteka.aws.domain.ShowEntity;
import java.util.List;
import java.util.Objects;

public class UpdateRequest {
  private List<ShowEntity> shows;
  private String userId;

  public UpdateRequest() {}

  public UpdateRequest(List<ShowEntity> shows, String userId) {
    this.shows = shows;
    this.userId = userId;
  }

  public List<ShowEntity> getShows() {
    return shows;
  }

  public void setShows(List<ShowEntity> shows) {
    this.shows = shows;
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
    UpdateRequest that = (UpdateRequest) o;
    return Objects.equals(shows, that.shows) && Objects.equals(userId, that.userId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(shows, userId);
  }

  @Override
  public String toString() {
    return "UpdateRequest{" + "shows=" + shows + ", userId='" + userId + '\'' + '}';
  }
}
