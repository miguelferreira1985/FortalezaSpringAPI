package com.fotaleza.fortalezaapi.service.impl;

import com.fotaleza.fortalezaapi.dto.request.UserRequestDTO;
import com.fotaleza.fortalezaapi.dto.request.EmployeeRequestDTO;
import com.fotaleza.fortalezaapi.dto.response.EmployeeResponseDTO;
import com.fotaleza.fortalezaapi.exception.ResourceAlreadyExistsException;
import com.fotaleza.fortalezaapi.exception.ResourceNotFoundException;
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
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements IEmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;
    private final IUserService userService;


    @Override
    @Transactional
    public EmployeeResponseDTO createEmployee(EmployeeRequestDTO employeeRequestDTO) {

        Employee employee = employeeMapper.toEntity(employeeRequestDTO);

        if (employeeRequestDTO.getUserRequestDTO() != null) {
            User user = userService.createUser(
                    employeeRequestDTO.getUserRequestDTO().getUsername(),
                    employeeRequestDTO.getUserRequestDTO().getPassword(),
                    employeeRequestDTO.getUserRequestDTO().getRoles()
            );
            employee.setUser(user);
        }

        Employee savedEmployed = employeeRepository.save(employee);
        return employeeMapper.toResponseDTO(savedEmployed);
    }

    @Override
    @Transactional
    public EmployeeResponseDTO addUserToEmployee(Integer employeeId, UserRequestDTO userRequestDTO) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("El empleado no existe."));

        if (employee.getIsActivate() != null) {
            throw new IllegalStateException("El empleado ya tiene un usuario asignado.");
        }

        User user = userService.createUser(
                userRequestDTO.getUsername(),
                userRequestDTO.getPassword(),
                userRequestDTO.getRoles()
        );

        employee.setUser(user);
        Employee updatedEmployee = employeeRepository.save(employee);

        return employeeMapper.toResponseDTO(updatedEmployee);
    }

    @Override
    @Transactional
    public EmployeeResponseDTO updateEmployee(Integer employeeId, EmployeeRequestDTO employeeRequestDTO) {
        Employee employeeToUpdate = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("El empleado no existe."));

        employeeMapper.updateEntityFromRequestDTO(employeeRequestDTO, employeeToUpdate);

        Employee updatedEmployee = employeeRepository.save(employeeToUpdate);
        return employeeMapper.toResponseDTO(updatedEmployee);
    }

    @Override
    public EmployeeResponseDTO getEmployeeById(Integer employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("El empleado no existe."));
        return employeeMapper.toResponseDTO(employee);
    }

    @Override
    public List<EmployeeResponseDTO> getAllEmployees(Boolean isActivate) {
        List<Employee> employees = Optional.ofNullable(isActivate)
                .map(employeeRepository::findByIsActivate)
                .orElseGet(employeeRepository::findAll);
        return employeeMapper.toResponseDTOList(employees);
    }

}
