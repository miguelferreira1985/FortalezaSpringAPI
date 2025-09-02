package com.fotaleza.fortalezaapi.service.impl;

import com.fotaleza.fortalezaapi.dto.EmployeeRequestDTO;
import com.fotaleza.fortalezaapi.dto.EmployeeResponseDTO;
import com.fotaleza.fortalezaapi.exception.EmployeeAlreadyExistsException;
import com.fotaleza.fortalezaapi.exception.EmployeeNotFoundException;
import com.fotaleza.fortalezaapi.mapper.EmployeeMapper;
import com.fotaleza.fortalezaapi.model.Employee;
import com.fotaleza.fortalezaapi.model.User;
import com.fotaleza.fortalezaapi.repository.EmployeeRepository;
import com.fotaleza.fortalezaapi.service.IEmployeeService;
import com.fotaleza.fortalezaapi.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class EmployeeServiceImpl implements IEmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;
    private final IUserService userService;


    @Override
    public EmployeeResponseDTO crreateEmployee(EmployeeRequestDTO employeeRequestDTO) {
        if (employeeRepository.findBySsn(employeeRequestDTO.getSsn()).isPresent()) {
            throw new EmployeeAlreadyExistsException("El NSS ya existe.");
        }

        User user = userService.createUser(
                employeeRequestDTO.getUserData().getUsername(),
                employeeRequestDTO.getUserData().getPassword(),
                employeeRequestDTO.getUserData().getRoles()
        );

        Employee employee = employeeMapper.toEntity(employeeRequestDTO);
        employee.setUser(user);
        employee.setIsActivate(true);

        Employee savedEmployed = employeeRepository.save(employee);
        return employeeMapper.toResponseDTO(savedEmployed);
    }

    @Override
    public EmployeeResponseDTO updateEmployee(Integer employeeId, EmployeeRequestDTO employeeRequestDTO) {
        Employee employeeToUpdate = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException("El empleado no existe."));

        employeeRepository.findBySsn(employeeRequestDTO.getSsn())
                .ifPresent(e -> {
                    throw new EmployeeAlreadyExistsException("El NSS ya esta registrado.");
                });

        employeeMapper.updateEntityFromRequestDTO(employeeRequestDTO, employeeToUpdate);

        Employee updatedEmployee = employeeRepository.save(employeeToUpdate);
        return employeeMapper.toResponseDTO(updatedEmployee);
    }

    @Override
    public void deleteEmployee(Integer employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException("El empleado no existe."));
        employee.setIsActivate(false);
        employeeRepository.save(employee);
    }

    @Override
    public EmployeeResponseDTO getEmployeeById(Integer employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException("El empleado no existe."));
        return employeeMapper.toResponseDTO(employee);
    }

    @Override
    public List<EmployeeResponseDTO> getAllEmployees(Boolean isActivate) {
        List<Employee> employees;

        if (isActivate != null) {
            employees = employeeRepository.findByIsActivate(isActivate);
        } else {
            employees = employeeRepository.findAll();
        }
        return employeeMapper.toResponseDTOList(employees);
    }
}
