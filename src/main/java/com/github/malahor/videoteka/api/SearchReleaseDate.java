package com.github.malahor.videoteka.api;

import com.fasterxml.jackson.annotation.JsonAlias;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.FormatStyle;
import java.util.Locale;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchReleaseDate {

  private static final String LANGUAGE = "pl";
  private static final DateTimeFormatter LOCAL_FORMATTER =
      DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM).localizedBy(Locale.of(LANGUAGE));

  @JsonAlias({"first_air_date", "release_date", "air_date"})
  private String value;

  public String formatted() {
    if (value == null || value.isEmpty()) return "";
    var localDate = localDate();
    if (isFuture(localDate)) return accurateDate(localDate);
    return year(localDate);
  }

  public boolean isFuture() {
    return isFuture(localDate());
  }

  private boolean isFuture(LocalDate localDate) {
    return localDate.isAfter(LocalDate.now());
  }

  private LocalDate localDate() {
    try {
      return LocalDate.parse(value, DateTimeFormatter.ISO_DATE);
    } catch (DateTimeParseException e) {
      return LocalDate.parse(value, LOCAL_FORMATTER);
    }
  }

  private String accurateDate(LocalDate localDate) {
    return localDate.format(LOCAL_FORMATTER);
  }

  private String year(LocalDate localDate) {
    return String.valueOf(localDate.getYear());
  }
}
