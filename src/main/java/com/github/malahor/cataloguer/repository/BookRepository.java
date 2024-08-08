package com.github.malahor.cataloguer.repository;

import com.github.malahor.cataloguer.domain.Book;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "books", path = "books")
public interface BookRepository extends CrudRepository<Book, Long> {

  List<Book> findByAuthor(@Param("author") String author);

  List<Book> findByTitle(@Param("title") String title);
}
