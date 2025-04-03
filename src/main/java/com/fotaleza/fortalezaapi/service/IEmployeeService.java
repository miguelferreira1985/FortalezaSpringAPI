package com.fotaleza.fortalezaapi.service;

import com.fotaleza.fortalezaapi.model.Employee;

import java.util.List;

public interface IEmployeeService {

    Employee saveEmployee(Employee employee);
    List<Employee> getAllEmployees(Boolean isActivate);
    Employee getEmployeeById(Integer employeeId);
    Boolean existsBySsn(String ssn);
    Employee updateEmployee(Employee employee);
    void deleteEmployee(Integer employeeId);

}
