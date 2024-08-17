package com.github.malahor.videoteka.api;

import com.github.malahor.videoteka.domain.ShowType;

import java.net.URI;

public interface UriResolver {

    URI search(String title, ShowType type);

    URI details(long id, ShowType type);
}
