package com.github.malahor.videoteka.domain;

import lombok.Data;

@Data
public class Show {

  private long id;
  private String title;
  private String releaseDate;
  private String poster;
  private double popularity;
  private ShowType showType;
}
