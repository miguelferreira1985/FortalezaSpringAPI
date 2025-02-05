package com.fotaleza.fortalezaapi.service.impl;

import com.fotaleza.fortalezaapi.model.Employee;
import com.fotaleza.fortalezaapi.repository.EmployeeRepository;
import com.fotaleza.fortalezaapi.service.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements IEmployeeService {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public List<Employee> getAllActiveEmployees() {
        return employeeRepository
                .findAll()
                .stream()
                .filter(employee -> employee.getIsActivate().equals(Boolean.TRUE))
                .toList();
    }

    @Override
    public List<Employee> getAllInactivateEmployees() {
        return List.of();
    }

    @Override
    public Employee getEmployeeById(Integer employeeId) {
        return null;
    }

    @Override
    public Boolean existsBySsn(String ssn) {
        return null;
    }

    @Override
    public Employee updateEmployee(Employee employee) {
        return null;
    }

    @Override
    public void deleteEmployee(Integer employeeId) {

    }

}
