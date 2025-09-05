package com.fotaleza.fortalezaapi.service.impl;

import com.fotaleza.fortalezaapi.dto.EmployeeRequestDTO;
import com.fotaleza.fortalezaapi.dto.EmployeeResponseDTO;
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
    public EmployeeResponseDTO crreateEmployee(EmployeeRequestDTO employeeRequestDTO) {
        validateSsnUnique(employeeRequestDTO.getSsn(), null);

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
    @Transactional
    public EmployeeResponseDTO updateEmployee(Integer employeeId, EmployeeRequestDTO employeeRequestDTO) {
        Employee employeeToUpdate = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("El empleado no existe."));

        validateSsnUnique(employeeRequestDTO.getSsn(), employeeId);

        employeeMapper.updateEntityFromRequestDTO(employeeRequestDTO, employeeToUpdate);

        Employee updatedEmployee = employeeRepository.save(employeeToUpdate);
        return employeeMapper.toResponseDTO(updatedEmployee);
    }

    @Override
    @Transactional
    public void deleteEmployee(Integer employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("El empleado no existe."));
        employee.setIsActivate(false);
        employeeRepository.save(employee);
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

    private void validateSsnUnique(String ssn, Integer employeeId) {
        employeeRepository.findBySsn(ssn)
                .ifPresent(e -> {
                    if (employeeId == null || e.getId().equals(employeeId)) {
                        throw new ResourceAlreadyExistsException("El empleado con el NSS ya existe.");
                    }
                });
    }
}
