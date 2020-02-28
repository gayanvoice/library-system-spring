package com.library.web.model;

import com.library.web.repository.EmployeeRepository;
import com.library.web.repository.MemberRepository;
import com.library.web.viewmodel.SignUpForm;
import com.library.web.viewmodel.UpdateEmployeeForm;
import com.library.web.viewmodel.UpdateMemberForm;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "employee")
public class Employee implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "employee_id", nullable = false)
    private long employeeId;
    private String username;
    private String password;
    private Date joined;
    private Date valid;
    private boolean status;

    public long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
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
        return "Employee [employeeId=" + employeeId + ", username=" + username +
                ", password=" + username + ", joined=" + joined.toString() + ", valid=" + valid.toString() + ", status=" + status + "]";
    }


    // we can not use AutoWired in an entity
    public static Employee from(SignUpForm signUpForm) {
        Employee employee = new Employee();
        employee.setUsername(signUpForm.getUsername());
        employee.setPassword(new BCryptPasswordEncoder().encode(signUpForm.getPassword()));
        employee.setJoined(new Date());
        employee.setValid(new Date());
        employee.setStatus(true);
        return employee;
    }

    public static Employee updateFrom(EmployeeRepository employeeRepository, UpdateEmployeeForm updateEmployeeForm) {
        Employee employee = employeeRepository.getByEmployeeId(updateEmployeeForm.getId());
        employee.setUsername(updateEmployeeForm.getUsername());
        employee.setPassword(updateEmployeeForm.getPassword());
        employee.setStatus(updateEmployeeForm.getStatus());
        return employee;
    }

}
