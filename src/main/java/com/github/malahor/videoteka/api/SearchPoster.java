package com.github.malahor.videoteka.api;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchPoster {

    private static final String POSTER_URI = "http://image.tmdb.org/t/p/w342";

    @JsonAlias("poster_path")
    private String path;

    public String full() {
        return POSTER_URI + path;
    }
}