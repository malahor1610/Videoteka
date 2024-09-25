package com.github.malahor.videoteka.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.FormatStyle;

public class DateHandler {

  public static LocalDate localDateOf(String releaseDate) {
    try {
      return LocalDate.parse(releaseDate, DateTimeFormatter.ISO_DATE);
    } catch (DateTimeParseException e) {
      return LocalDate.parse(releaseDate, DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM));
    }
  }

  public static String accurateDate(String releaseDate) {
    return localDateOf(releaseDate).format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM));
  }
}
