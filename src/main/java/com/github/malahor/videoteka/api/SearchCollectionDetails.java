package com.github.malahor.videoteka.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchCollectionDetails {

    private long id;
    private String name;
    private List<SearchResultShow> parts;
}
