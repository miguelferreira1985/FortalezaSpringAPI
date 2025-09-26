package com.fotaleza.fortalezaapi.service;

import com.fotaleza.fortalezaapi.dto.request.EmployeeRequestDTO;
import com.fotaleza.fortalezaapi.dto.response.EmployeeResponseDTO;

import java.util.List;

public interface IEmployeeService {

    EmployeeResponseDTO createEmployee(EmployeeRequestDTO employeeDTO);
    EmployeeResponseDTO updateEmployee(Integer employeeId, EmployeeRequestDTO employeeDTO);
    void deleteEmployee(Integer employeeId);
    EmployeeResponseDTO getEmployeeById(Integer employeeId);
    List<EmployeeResponseDTO> getAllEmployees(Boolean isActivate);

}
