package com.github.malahor.cataloguer.repository;

import com.github.malahor.cataloguer.domain.Platform;
import com.github.malahor.cataloguer.domain.Video;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "videos", path = "videos")
public interface VideoRepository extends CrudRepository<Video, Long> {

  List<Video> findByPlatform(Platform platform);

  List<Video> findByTypeStartsWith(String type);
}
