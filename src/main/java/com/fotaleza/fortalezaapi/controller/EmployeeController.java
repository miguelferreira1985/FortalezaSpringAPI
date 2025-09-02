package com.fotaleza.fortalezaapi.controller;

import com.fotaleza.fortalezaapi.dto.EmployeeRequestDTO;
import com.fotaleza.fortalezaapi.dto.EmployeeResponseDTO;
import com.fotaleza.fortalezaapi.service.impl.EmployeeServiceImpl;
import com.fotaleza.fortalezaapi.service.impl.RoleServiceImpl;
import com.fotaleza.fortalezaapi.service.impl.UserServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.*;

@RestController
@RequestMapping("/api/v1/employee")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeServiceImpl employeeService;
    private final UserServiceImpl userService;
    private final RoleServiceImpl roleService;
    private final PasswordEncoder encoder;

    @PostMapping()
    public ResponseEntity<EmployeeResponseDTO> createEmployeeAndUser(@Valid @RequestBody EmployeeRequestDTO employeeRequestDTO) {
        EmployeeResponseDTO employeeCreated = employeeService.crreateEmployee(employeeRequestDTO);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(employeeCreated.getId())
                .toUri();
        return ResponseEntity.created(location).body(employeeCreated);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponseDTO> getEmployeeById(@PathVariable Integer id) {
        EmployeeResponseDTO employee = employeeService.getEmployeeById(id);
        return ResponseEntity.ok(employee);
    }

    @GetMapping
    @RequestMapping("/getAllEmployees")
    public ResponseEntity<List<EmployeeResponseDTO>> getAllEmployees(@RequestParam(name = "isActivate", required = false) Boolean isActivate) {
        List<EmployeeResponseDTO> employees = employeeService.getAllEmployees(isActivate);
        return ResponseEntity.ok(employees);
    }

}
