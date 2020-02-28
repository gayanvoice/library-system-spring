package com.library.web.viewmodel;

import com.library.web.model.Author;
import com.library.web.model.Member;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.SecondaryTable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

public class UpdateMemberForm {

    @NotNull
    private long id;

    @NotNull
    @Size(min = 6, max = 40)
    private String username;



    @NotNull
    @Size(min = 6, max = 512)
    private String password;


    @NotNull
    private boolean status;


    public UpdateMemberForm() { }

    public UpdateMemberForm(@NotNull long id, @NotNull @Size(min = 6, max = 40) String username,
                            @NotNull @Size(min = 6, max = 512) String password, @NotNull boolean status) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.status = status;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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

    public static UpdateMemberForm from(Member member) {
        return new UpdateMemberForm(member.getMemberId(), member.getUsername(), member.getPassword(),
                member.getStatus());
    }
}