package com.library.web.repository;

import com.library.web.model.Shelf;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShelfRepository extends CrudRepository<Shelf, Long> {
    Optional<Shelf> findByShelfId(long shelfId);

    Optional<Shelf> findByCode(String code);
}