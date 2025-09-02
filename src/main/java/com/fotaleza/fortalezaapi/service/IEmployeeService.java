package com.fotaleza.fortalezaapi.service;

import com.fotaleza.fortalezaapi.dto.EmployeeRequestDTO;
import com.fotaleza.fortalezaapi.dto.EmployeeResponseDTO;

import java.util.List;

public interface IEmployeeService {

    EmployeeResponseDTO crreateEmployee(EmployeeRequestDTO employeeDTO);
    EmployeeResponseDTO updateEmployee(Integer employeeId, EmployeeRequestDTO employeeDTO);
    void deleteEmployee(Integer employeeId);
    EmployeeResponseDTO getEmployeeById(Integer employeeId);
    List<EmployeeResponseDTO> getAllEmployees(Boolean isActivate);

}
