package com.github.malahor.videoteka.domain;

import com.github.malahor.videoteka.api.SearchDetails;

public enum ShowStatus {
  UNLOCKED,
  LOCKED_NOT_IN_PRODUCTION,
  LOCKED_CHANGED_IN_PRODUCTION,
  LOCKED_IN_PRODUCTION,
  LOCKED_CHANGE_DATE,
  LOCKED_DATE_CONFIRMED;

  public static ShowStatus lockByDetails(SearchDetails details) {
    if (!details.getInProduction()) return LOCKED_NOT_IN_PRODUCTION;
    if (details.getContinuation() == null) return LOCKED_IN_PRODUCTION;
    return LOCKED_DATE_CONFIRMED;
  }
}
