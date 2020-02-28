package com.library.web.model;

import com.library.web.repository.AuthorRepository;
import com.library.web.repository.MemberRepository;
import com.library.web.viewmodel.CreateMemberForm;
import com.library.web.viewmodel.UpdateAuthorForm;
import com.library.web.viewmodel.UpdateMemberForm;
import org.springframework.data.type.classreading.MethodsMetadataReader;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "member")
public class Member implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "member_id", nullable = false)
    private long memberId;
    private String username;
    private String password;
    private Date joined;
    private Date valid;
    private boolean status;

    public long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
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

    public Date getJoined() {
        return joined;
    }

    public void setJoined(Date joined) {
        this.joined = joined;
    }

    public Date getValid() {
        return valid;
    }

    public void setValid(Date valid) {
        this.valid = valid;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Member [memberId=" + memberId + ", username=" + username +
                ", password=" + username + ", joined=" + joined.toString() + ", valid=" + valid.toString() + ", status=" + status + "]";
    }

    public static Member from(CreateMemberForm createMemberForm) {
        Member member = new Member();
        member.setUsername(createMemberForm.getUsername());
        member.setPassword(new BCryptPasswordEncoder().encode(createMemberForm.getPassword()));
        member.setJoined(new Date());
        member.setValid(new Date());
        member.setStatus(createMemberForm.getStatus());
        return member;
    }

    // keeping null is not okay, but any other way to keep this fine?
    // can not use optionals, it creates duplicate data
    public static Member updateFrom(MemberRepository memberRepository, UpdateMemberForm updateMemberForm) {
        Member member = memberRepository.findByMemberId(updateMemberForm.getId());
        member.setUsername(updateMemberForm.getUsername());
        member.setPassword(updateMemberForm.getPassword());
        member.setStatus(updateMemberForm.getStatus());
        return member;
    }

}
