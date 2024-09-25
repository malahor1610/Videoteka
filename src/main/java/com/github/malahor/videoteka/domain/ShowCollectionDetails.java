package com.github.malahor.videoteka.domain;

import java.util.List;
import lombok.Data;

@Data
public class ShowCollectionDetails {

  private long id;
  private String name;
  private List<Show> parts;
}
