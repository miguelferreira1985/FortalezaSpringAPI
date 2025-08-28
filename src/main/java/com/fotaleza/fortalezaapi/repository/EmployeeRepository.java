package com.fotaleza.fortalezaapi.repository;

import com.fotaleza.fortalezaapi.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    boolean existsBySsn(String ssn);

}
