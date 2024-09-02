package com.github.malahor.videoteka.aws;

import com.github.malahor.videoteka.aws.domain.Show;
import com.github.malahor.videoteka.aws.domain.ShowDetails;
import com.github.malahor.videoteka.aws.domain.ShowEntity;
import com.github.malahor.videoteka.aws.domain.ShowType;
import com.github.malahor.videoteka.aws.repository.ShowRepository;
import com.github.malahor.videoteka.aws.tmdb_api.TMDbDomainMapper;
import com.github.malahor.videoteka.aws.tmdb_api.TMDbService;
import java.util.List;

public class ShowService {

  public void save(ShowEntity show) {
    repository().save(show);
  }

  public void updateShow(ShowEntity show) {
    repository().updateShow(show);
  }

  public void deleteById(long id, String user) {
    repository().deleteById(id, user);
  }

  public List<ShowEntity> findAll(String user) {
    return repository().findAll(user);
  }

  public List<ShowEntity> findByType(ShowType type, String user) {
    return repository().findByType(type, user);
  }

  public List<Show> search(String title, ShowType type, String apiKey) {
    var result = apiService(apiKey).search(title, type);
    return new TMDbDomainMapper().mapSearchResult(result, type);
  }

  public ShowDetails details(long id, ShowType type, String apiKey) {
    var result = apiService(apiKey).details(id, type);
    return new TMDbDomainMapper().mapSearchDetails(result, type);
  }

  private ShowRepository repository() {
    return new ShowRepository();
  }

  private TMDbService apiService(String apiKey) {
    return new TMDbService(apiKey);
  }
}
