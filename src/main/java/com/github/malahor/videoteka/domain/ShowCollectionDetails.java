package com.github.malahor.videoteka.domain;

import lombok.Data;

import java.util.List;

@Data
public class ShowCollectionDetails {

    private long id;
    private String name;
    private List<Show> parts;
}
