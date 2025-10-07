package com.fotaleza.fortalezaapi.service;

import com.fotaleza.fortalezaapi.dto.request.UserRequestDTO;
import com.fotaleza.fortalezaapi.dto.request.EmployeeRequestDTO;
import com.fotaleza.fortalezaapi.dto.response.EmployeeResponseDTO;

import java.util.List;

public interface IEmployeeService {

    EmployeeResponseDTO createEmployee(EmployeeRequestDTO employeeDTO);
    EmployeeResponseDTO updateEmployee(Integer employeeId, EmployeeRequestDTO employeeDTO);
    EmployeeResponseDTO getEmployeeById(Integer employeeId);
    EmployeeResponseDTO addUserToEmployee(Integer employeeId, UserRequestDTO userRequestDTO);
    List<EmployeeResponseDTO> getAllEmployees(Boolean isActivate);

}
