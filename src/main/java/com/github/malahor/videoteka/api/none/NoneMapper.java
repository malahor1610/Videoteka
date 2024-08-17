package com.github.malahor.videoteka.api.none;

import com.github.malahor.videoteka.api.DomainMapper;
import com.github.malahor.videoteka.api.SearchDetails;
import com.github.malahor.videoteka.api.SearchResult;
import com.github.malahor.videoteka.domain.Show;
import com.github.malahor.videoteka.domain.ShowDetails;
import com.github.malahor.videoteka.domain.ShowType;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Component
@ConditionalOnProperty(value = "api.used", havingValue = "none", matchIfMissing = true)
public class NoneMapper implements DomainMapper {
  @Override
  public List<Show> mapSearchResult(SearchResult searchResult, ShowType type) {
    throw new UnsupportedOperationException();
  }

  @Override
  public ShowDetails mapSearchDetails(SearchDetails searchDetails, ShowType type) {
    throw new UnsupportedOperationException();
  }
}
