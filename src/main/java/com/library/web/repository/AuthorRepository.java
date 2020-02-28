package com.library.web.repository;

import com.library.web.model.Author;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorRepository extends CrudRepository<Author, Long> {

    Optional<Author> findByAuthorId(long authorId);

    Optional<Author> findByName(String username);

}