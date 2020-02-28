package com.library.web.model;

import com.library.web.repository.AuthorRepository;
import com.library.web.repository.InventoryRepository;
import com.library.web.viewmodel.CreateAuthorForm;
import com.library.web.viewmodel.CreateInventoryForm;
import com.library.web.viewmodel.UpdateAuthorForm;
import com.library.web.viewmodel.UpdateInventoryForm;

import javax.persistence.*;

@Entity
@Table(name = "inventory")
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "inventory_id", nullable = false)
    private long inventoryId;

    @Column(name = "book_id", nullable = false)
    private long bookId;

    @Column(name = "shelf_id", nullable = false)
    private long shelfId;

    @Column(name = "is_lend", nullable = false)
    private boolean isLend;
    private boolean status;

    public long getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(long inventoryId) {
        this.inventoryId = inventoryId;
    }

    public long getBookId() {
        return bookId;
    }

    public void setBookId(long bookId) {
        this.bookId = bookId;
    }

    public long getShelfId() {
        return shelfId;
    }

    public void setShelfId(long shelfId) {
        this.shelfId = shelfId;
    }

    public boolean getIsLend() {
        return isLend;
    }

    public void setIsLend(boolean isLend) {
        this.isLend = isLend;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public static Inventory from(CreateInventoryForm createInventoryForm) {
        Inventory inventory = new Inventory();
        inventory.setBookId(createInventoryForm.getBookId());
        inventory.setShelfId(createInventoryForm.getShelfId());
        inventory.setIsLend(createInventoryForm.getIsLend());
        inventory.setStatus(createInventoryForm.getStatus());
        return inventory;
    }

    // keeping null is not okay, but any other way to keep this fine?
    public static Inventory updateFrom(InventoryRepository inventoryRepository, UpdateInventoryForm updateInventoryForm) {
        return inventoryRepository.findByInventoryId(updateInventoryForm.getInventoryId())
                .map(a -> {
                    Inventory inventory = new Inventory();
                    inventory.setInventoryId(updateInventoryForm.getInventoryId());
                    inventory.setBookId(updateInventoryForm.getBookId());
                    inventory.setShelfId(updateInventoryForm.getShelfId());
                    inventory.setIsLend(updateInventoryForm.getIsLend());
                    inventory.setStatus(updateInventoryForm.getStatus());
                    return inventory;
                })
                .orElse(null);
    }
}
