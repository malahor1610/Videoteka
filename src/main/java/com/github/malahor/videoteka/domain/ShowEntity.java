package com.github.malahor.videoteka.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "show")
public class ShowEntity {

  @Id private long id;
  private String title;
  private String originalTitle;
  private String releaseDate;
  private String poster;

  @Enumerated(EnumType.STRING)
  private ShowType type;

  private int position;
}
