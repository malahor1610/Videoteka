package com.github.malahor.videoteka.api;

import java.util.List;
import lombok.Data;

@Data
public class SearchCollectionDetails {

  private long id;
  private String name;
  private List<SearchResultShow> parts;
}
