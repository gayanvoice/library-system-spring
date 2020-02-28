package com.library.web.repository;

import com.library.web.model.Author;
import com.library.web.model.Inventory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InventoryRepository extends CrudRepository<Inventory, Long> {

    Optional<Inventory> findByInventoryId(long bookId);

    Optional<Inventory> findByBookId(long bookId);

}