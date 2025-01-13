package com.github.malahor.videoteka.domain;

import com.github.malahor.videoteka.api.SearchReleaseDate;

public enum ShowLockState {
  UNLOCKED,
  LOCKED_NOT_IN_PRODUCTION,
  LOCKED_CHANGED_IN_PRODUCTION,
  LOCKED_IN_PRODUCTION,
  LOCKED_CHANGED_DATE,
  LOCKED_DATE_CONFIRMED,
  LOCKED_CHANGED_RELEASED;

  public static boolean isLocked(ShowEntity show) {
    return show.getLockState() != null && !show.getLockState().equals(UNLOCKED);
  }

  public static ShowLockState lockByDetails(ShowDetails details) {
    if (details.getContinuation() == null || !details.getContinuation().isInProduction())
      return LOCKED_NOT_IN_PRODUCTION;
    if (details.getContinuation().getReleaseDate() == null
        || details.getContinuation().getReleaseDate().isEmpty()) return LOCKED_IN_PRODUCTION;
    if (new SearchReleaseDate(details.getContinuation().getReleaseDate()).isFuture())
      return LOCKED_DATE_CONFIRMED;
    return UNLOCKED;
  }

  public ShowLockState changed() {
    return switch (this) {
      case LOCKED_CHANGED_IN_PRODUCTION -> LOCKED_IN_PRODUCTION;
      case LOCKED_IN_PRODUCTION -> LOCKED_CHANGED_IN_PRODUCTION;
      case LOCKED_CHANGED_DATE -> LOCKED_DATE_CONFIRMED;
      case LOCKED_DATE_CONFIRMED -> LOCKED_CHANGED_DATE;
      case LOCKED_CHANGED_RELEASED -> UNLOCKED;
      case LOCKED_NOT_IN_PRODUCTION, UNLOCKED -> LOCKED_CHANGED_RELEASED;
    };
  }
}
