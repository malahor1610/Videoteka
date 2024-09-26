package com.github.malahor.videoteka.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.FormatStyle;
import java.util.Locale;

public class DateHandler {

  private static final String LANGUAGE = "pl";

  public static LocalDate localDateOf(String releaseDate) {
    try {
      return LocalDate.parse(releaseDate, DateTimeFormatter.ISO_DATE);
    } catch (DateTimeParseException e) {
      return LocalDate.parse(releaseDate, localFormat());
    }
  }

  public static String accurateDate(String releaseDate) {
    return localDateOf(releaseDate).format(localFormat());
  }

  private static DateTimeFormatter localFormat() {
    return DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM).localizedBy(Locale.of(LANGUAGE));
  }
}
