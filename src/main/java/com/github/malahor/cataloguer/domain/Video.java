package com.github.malahor.cataloguer.domain;

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
public class Video {

  @Id @GeneratedValue private long id;

  private String title;
  private int position;

  @Enumerated(EnumType.STRING)
  private Type type;

  @Enumerated(EnumType.STRING)
  private Platform platform;
}
