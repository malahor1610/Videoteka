package com.github.malahor.videoteka.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class WatchProviders {
  private List<String> available;
  private List<String> rent;
  private List<String> buy;
}
