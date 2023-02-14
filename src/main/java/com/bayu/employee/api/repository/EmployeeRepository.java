package com.bayu.employee.api.repository;

import com.bayu.employee.api.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> {

    Optional<Employee> findByNik(String nik);

    Optional<Employee> findByEmail(String email);

    Optional<Employee> findByName(String name);

    Boolean existsByNik(String nik);

    Boolean existsByEmail(String email);
}
