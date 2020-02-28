package com.library.web.repository;

import com.library.web.model.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {

    Book getByBookId(long bookid);

    Optional<Book> findByBookId(long bookId);

    Optional<Book> findByTitle(String title);

}