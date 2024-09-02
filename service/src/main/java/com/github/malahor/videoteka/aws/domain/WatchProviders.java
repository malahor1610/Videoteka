package com.github.malahor.videoteka.aws.domain;

import java.util.Collections;
import java.util.List;

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

  public List<String> getAvailable() {
    return available;
  }

  public void setAvailable(List<String> available) {
    this.available = available;
  }

  public List<String> getRent() {
    return rent;
  }

  public void setRent(List<String> rent) {
    this.rent = rent;
  }

  public List<String> getBuy() {
    return buy;
  }

  public void setBuy(List<String> buy) {
    this.buy = buy;
  }
}
