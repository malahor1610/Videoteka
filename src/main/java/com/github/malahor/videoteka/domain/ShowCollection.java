package com.github.malahor.videoteka.domain;

import com.github.malahor.videoteka.api.SearchCollection;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ShowCollection {

  private long id;
  private String name;

  public static ShowCollection from(SearchCollection collection) {
    if (collection == null) return null;
    return ShowCollection.builder().id(collection.getId()).name(collection.getName()).build();
  }
}
