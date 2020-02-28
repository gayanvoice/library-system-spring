package com.library.web.viewmodel;

import com.library.web.model.Author;
import com.library.web.model.Shelf;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UpdateShelfForm {

    @NotNull
    private long id;

    @NotNull
    @Size(min = 4, max = 10)
    private String code;

    public UpdateShelfForm() {
    }

    public UpdateShelfForm(long id, String code) {
        this.id = id;
        this.code = code;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public static UpdateShelfForm from(Shelf shelf) {
        return new UpdateShelfForm(shelf.getShelfId(), shelf.getCode());
    }

}