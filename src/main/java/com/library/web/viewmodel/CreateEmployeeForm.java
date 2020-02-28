package com.library.web.viewmodel;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CreateEmployeeForm {

    @NotNull
    @Size(min = 6, max = 40)
    private String username;

    @NotNull
    @Size(min = 6, max = 20)
    private String password;

    private boolean status;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}