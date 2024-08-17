package com.github.malahor.videoteka.api.none;

import com.github.malahor.videoteka.api.ApiService;
import com.github.malahor.videoteka.api.tmdb.TMDbSearchResult;
import com.github.malahor.videoteka.api.tmdb.TMDbSearchDetails;
import com.github.malahor.videoteka.domain.ShowType;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(value = "api.used", havingValue = "none", matchIfMissing = true)
public class NoneService implements ApiService {
  @Override
  public TMDbSearchResult search(String title, ShowType type) {
    throw new UnsupportedOperationException();
  }

  @Override
  public TMDbSearchDetails details(long id, ShowType type) {
    throw new UnsupportedOperationException();
  }
}
