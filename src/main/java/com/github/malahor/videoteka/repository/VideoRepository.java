package com.github.malahor.videoteka.repository;

import com.github.malahor.videoteka.domain.Video;
import com.github.malahor.videoteka.the_movie_db.SearchType;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

@RepositoryRestResource(collectionResourceRel = "videos", path = "videos")
public interface VideoRepository extends CrudRepository<Video, Long> {

  @RestResource(path = "byType", rel = "byType")
  List<Video> findByType(SearchType type);

  @Query(value = "SELECT MAX(v.position) FROM Video v")
  int findMaxPosition();
}
