package com.github.malahor.videoteka.domain;

import com.github.malahor.videoteka.api.SearchWatchProviders;
import java.util.Collections;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ShowWatchProviders {
  private List<String> available;
  private List<String> rent;
  private List<String> buy;

  public static ShowWatchProviders from(SearchWatchProviders watchProviders) {
    if (watchProviders == null) return noProviders();
    return ShowWatchProviders.builder()
        .available(watchProviders.getFlatrate())
        .rent(watchProviders.getRent())
        .buy(watchProviders.getBuy())
        .build();
  }

  private static ShowWatchProviders noProviders() {
    return ShowWatchProviders.builder()
        .available(Collections.emptyList())
        .rent(Collections.emptyList())
        .buy(Collections.emptyList())
        .build();
  }
}
