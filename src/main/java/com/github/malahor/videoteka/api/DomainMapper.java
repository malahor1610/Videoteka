package com.github.malahor.videoteka.api;

import com.github.malahor.videoteka.domain.Show;
import com.github.malahor.videoteka.domain.ShowDetails;
import com.github.malahor.videoteka.domain.ShowType;

import java.util.List;

public interface DomainMapper {

    List<Show> mapSearchResult(SearchResult searchResult, ShowType type);

    ShowDetails mapSearchDetails(SearchDetails searchDetails, ShowType type);
}
