package com.github.malahor.videoteka.domain;

import java.util.List;
import lombok.*;

@Data
public class ShowDetails {

  private long id;
  private String originalTitle;
  private String title;
  private String overview;
  private String releaseDate;
  private ShowContinuation continuation;
  private ShowLockState status;
  private String duration;
  private ShowType showType;
  private List<String> genres;
  private WatchProviders watchProviders;
  private ShowCollection collection;
  private List<ShowSeason> seasons;
  private ShowWatchState watchState;
}
