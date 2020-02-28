package com.library.web.viewmodel;

import com.library.web.model.Author;
import com.library.web.model.Inventory;

import javax.validation.constraints.NotNull;

public class UpdateInventoryForm {

    @NotNull
    private long inventoryId;

    @NotNull
    private long bookId;

    @NotNull
    private long shelfId;

    @NotNull
    private boolean isLend;

    @NotNull
    private boolean status;

    public UpdateInventoryForm() { }

    public UpdateInventoryForm(@NotNull long inventoryId, @NotNull long bookId, @NotNull long shelfId,
                               @NotNull boolean isLend, @NotNull boolean status) {
        this.inventoryId = inventoryId;
        this.bookId = bookId;
        this.shelfId = shelfId;
        this.isLend = isLend;
        this.status = status;
    }


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

    public void setIsLend(boolean isLent) {
        this.isLend = isLent;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public static UpdateInventoryForm from(Inventory inventory) {
        return new UpdateInventoryForm(inventory.getInventoryId(), inventory.getBookId(), inventory.getShelfId(),
                inventory.getIsLend(), inventory.getStatus());
    }
}