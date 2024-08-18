package com.github.malahor.videoteka.domain;

import java.util.Collections;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WatchProviders {
  private List<String> available;
  private List<String> rent;
  private List<String> buy;

  public static WatchProviders noProviders() {
    var watchProviders = new WatchProviders();
    watchProviders.available = Collections.emptyList();
    watchProviders.rent = Collections.emptyList();
    watchProviders.buy = Collections.emptyList();
    return watchProviders;
  }
}
