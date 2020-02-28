package com.library.web.viewmodel;

import com.library.web.model.Author;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UpdateAuthorForm {

    @NotNull
    private long id;

    @NotNull
    @Size(min = 6, max = 40)
    private String name;

    @NotNull
    @Size(min = 6, max = 1000)
    private String description;

    public UpdateAuthorForm() {
    }

    public UpdateAuthorForm(@NotNull long id, @NotNull @Size(min = 6, max = 40) String name, @NotNull @Size(min = 6, max = 1000) String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String descrption) {
        this.description = descrption;
    }

    public static UpdateAuthorForm from(Author author) {
        return new UpdateAuthorForm(author.getAuthorId(), author.getName(), author.getDescription());
    }


}