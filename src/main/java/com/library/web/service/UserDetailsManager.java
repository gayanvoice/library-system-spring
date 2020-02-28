package com.library.web.service;

import com.library.web.model.User;
import com.library.web.model.Employee;
import com.library.web.repository.EmployeeRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// The implementations of UseDetailsService used Service, Manager or Adapter
@Service
public class UserDetailsManager implements UserDetailsService {

    private final EmployeeRepository employeeRepository;

    public UserDetailsManager(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Employee employee = employeeRepository.getByUsername(username);

        if (employee == null) {
            throw new UsernameNotFoundException("User not found.");
        }

        return new User(employee);

    }

}