package com.github.malahor.videoteka.repository;

import com.github.malahor.videoteka.domain.ShowEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Component
public class ShowIdExposeConfiguration implements RepositoryRestConfigurer {

    @Value("${cors.origin}")
    private String origin;

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
        config.exposeIdsFor(ShowEntity.class);
        cors.addMapping("/api/**").allowedOrigins(origin).allowedMethods("GET", "POST", "DELETE");
        RepositoryRestConfigurer.super.configureRepositoryRestConfiguration(config, cors);
    }

}
