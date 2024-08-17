package com.github.malahor.videoteka.repository;

import com.github.malahor.videoteka.domain.ShowEntity;
import com.github.malahor.videoteka.domain.ShowType;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

@RepositoryRestResource(collectionResourceRel = "shows", path = "shows")
public interface ShowRepository extends CrudRepository<ShowEntity, Long> {

  @RestResource(path = "byType", rel = "byType")
  List<ShowEntity> findByType(ShowType type);

  @Query(value = "SELECT MAX(s.position) FROM ShowEntity s")
  int findMaxPosition();
}
