package com.library.web.viewmodel;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class SignUpForm {

    @NotNull
    @Size(min = 6, max = 20)
    @Pattern(regexp = "^[A-Za-z0-9]*$", message = "Insert valid username")
    private String username;

    @NotNull
    @Size(min = 6, max = 20)
    private String password;

    @NotNull
    @Size(min = 6, max = 20)

    private String confirmPassword;

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

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    // tried isValid
    // validation works, but the message is not displaying
    @AssertTrue(message = "Passwords are not valid")
    public boolean getValid() {
        return this.getPassword().equals(this.getConfirmPassword());
    }
}