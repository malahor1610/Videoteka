package com.github.malahor.videoteka.api;

import com.github.malahor.videoteka.domain.ShowType;

public interface ApiService {

    SearchResult search(String title, ShowType type);

    SearchDetails details(long id, ShowType type);
}
