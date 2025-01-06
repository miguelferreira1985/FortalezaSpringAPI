package com.fotaleza.fortalezaapi.service.impl;

import com.fotaleza.fortalezaapi.model.Employee;
import com.fotaleza.fortalezaapi.repository.EmployeeRepository;
import com.fotaleza.fortalezaapi.service.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements IEmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

}
