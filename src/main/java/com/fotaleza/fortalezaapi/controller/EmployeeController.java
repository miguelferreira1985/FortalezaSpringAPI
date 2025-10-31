package com.fotaleza.fortalezaapi.controller;

import com.fotaleza.fortalezaapi.dto.request.UserRequestDTO;
import com.fotaleza.fortalezaapi.dto.request.EmployeeRequestDTO;
import com.fotaleza.fortalezaapi.dto.response.ApiResponse;
import com.fotaleza.fortalezaapi.dto.response.EmployeeResponseDTO;
import com.fotaleza.fortalezaapi.service.IEmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/api/v1/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final IEmployeeService employeeService;

    @PostMapping()
    public ResponseEntity<ApiResponse<EmployeeResponseDTO>> createEmployeeAndUser(@Valid @RequestBody EmployeeRequestDTO employeeRequestDTO) {

        EmployeeResponseDTO createdEmployee = employeeService.createEmployee(employeeRequestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.<EmployeeResponseDTO>builder()
                        .status(HttpStatus.CREATED.value())
                        .message("Empleado creado existosamente.")
                        .data(createdEmployee)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @PutMapping("/{id}/user")
    public ResponseEntity<ApiResponse<EmployeeResponseDTO>> addUserToEmployee(
            @PathVariable Integer id,
            @Valid @RequestBody UserRequestDTO userRequestDTO) {

        EmployeeResponseDTO updatedEmployee =  employeeService.addUserToEmployee(id, userRequestDTO);

        return ResponseEntity.ok(
                ApiResponse.<EmployeeResponseDTO>builder()
                        .status(HttpStatus.OK.value())
                        .message("Usuario asignado al empleado exitosamente.")
                        .data(updatedEmployee)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<EmployeeResponseDTO>> updateEmployee(
            @PathVariable Integer id,
            @Valid @RequestBody EmployeeRequestDTO employeeRequestDTO) {

        EmployeeResponseDTO updatedEmployee = employeeService.updateEmployee(id, employeeRequestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.<EmployeeResponseDTO>builder()
                        .status(HttpStatus.CREATED.value())
                        .message("Empleado actualizado existosamente.")
                        .data(updatedEmployee)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<EmployeeResponseDTO>> getEmployeeById(@PathVariable Integer id) {

        EmployeeResponseDTO employee = employeeService.getEmployeeById(id);

        return ResponseEntity.ok(
                ApiResponse.<EmployeeResponseDTO>builder()
                        .status(HttpStatus.OK.value())
                        .message("Empleado obtenido existosamente.")
                        .data(employee)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<EmployeeResponseDTO>>> getAllEmployees(@RequestParam(name = "isActivate", required = false) Boolean isActivate) {

        List<EmployeeResponseDTO> employees = employeeService.getAllEmployees(isActivate);

        return ResponseEntity.ok(
                ApiResponse.<List<EmployeeResponseDTO>>builder()
                        .status(HttpStatus.OK.value())
                        .message("Empleados obtenidos existosamente.")
                        .data(employees)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

}
