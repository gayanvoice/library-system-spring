package com.library.web.viewmodel;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CreateShelfForm {

    @NotNull
    @Size(min = 4, max = 10)
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


}