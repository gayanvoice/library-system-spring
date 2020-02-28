package com.library.web.viewmodel;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CreateAuthorForm {

    @NotNull
    @Size(min = 6, max = 40)
    private String name;

    @NotNull
    @Size(min = 6, max = 1000)
    private String description;

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

}