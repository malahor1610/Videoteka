package com.github.malahor.videoteka.api;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchCollectionDetails {

  private long id;
  private String name;
  private List<SearchResultShow> parts;
}
