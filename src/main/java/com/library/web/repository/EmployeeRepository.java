package com.library.web.repository;

import com.library.web.model.Employee;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Long> {
    Employee getByUsername(String username);

    Employee getByEmployeeId(long employeeId);

    Optional<Employee> findByUsername(String username);

    Optional<Employee> findByEmployeeId(long employeeId);
}