package com.github.malahor.videoteka.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public enum Platform {
  @JsonProperty("Netflix")
  NETFLIX,
  @JsonProperty("Max")
  MAX,
  @JsonProperty("Disney+")
  DISNEY,
  @JsonProperty("Amazon Prime")
  PRIME;
}
