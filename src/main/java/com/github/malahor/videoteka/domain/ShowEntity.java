package com.github.malahor.videoteka.domain;

import com.github.malahor.videoteka.the_movie_db.SearchType;
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
  private SearchType type;

  private int position;
}
