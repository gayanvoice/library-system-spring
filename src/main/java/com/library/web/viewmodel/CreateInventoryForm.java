package com.library.web.viewmodel;

import javax.validation.constraints.NotNull;

public class CreateInventoryForm {

    @NotNull
    private long bookId;

    @NotNull
    private long shelfId;

    @NotNull
    private boolean isLend;

    @NotNull
    private boolean status;

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
}