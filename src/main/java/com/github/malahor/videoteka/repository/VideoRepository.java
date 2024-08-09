package com.github.malahor.videoteka.repository;

import com.github.malahor.videoteka.domain.Platform;
import com.github.malahor.videoteka.domain.Type;
import com.github.malahor.videoteka.domain.Video;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

@RepositoryRestResource(collectionResourceRel = "videos", path = "videos")
public interface VideoRepository extends CrudRepository<Video, Long> {

  @RestResource(path = "byPlatform", rel = "byPlatform")
  List<Video> findByPlatform(Platform platform);

  @RestResource(path = "byType", rel = "byType")
  List<Video> findByType(Type type);
}
