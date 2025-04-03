package com.fotaleza.fortalezaapi.controller;

import com.fotaleza.fortalezaapi.dto.request.EmployeeRequestDto;
import com.fotaleza.fortalezaapi.dto.response.EmployeeResponseDto;
import com.fotaleza.fortalezaapi.dto.response.MessageResponse;
import com.fotaleza.fortalezaapi.mapper.EmployeeMapperDto;
import com.fotaleza.fortalezaapi.model.ERole;
import com.fotaleza.fortalezaapi.model.Employee;
import com.fotaleza.fortalezaapi.model.Role;
import com.fotaleza.fortalezaapi.model.User;
import com.fotaleza.fortalezaapi.service.impl.EmployeeServiceImpl;
import com.fotaleza.fortalezaapi.service.impl.RoleServiceImpl;
import com.fotaleza.fortalezaapi.service.impl.UserServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/v1/employee")
public class EmployeeController {

    private final EmployeeServiceImpl employeeService;
    private final UserServiceImpl userService;
    private final RoleServiceImpl roleService;
    private final PasswordEncoder encoder;

    @Autowired
    public EmployeeController(EmployeeServiceImpl employeeService, UserServiceImpl userService, RoleServiceImpl roleService, PasswordEncoder encoder) {
        this.employeeService = employeeService;
        this.userService = userService;
        this.roleService = roleService;
        this.encoder = encoder;
    }

    @GetMapping
    @RequestMapping("/getAllEmployees")
    public ResponseEntity<?> getAllEmployees(@RequestParam("isActivate") boolean isActivate) {

        List<EmployeeResponseDto> employeeResponseDtoList = EmployeeMapperDto.toModelList(employeeService.getAllEmployees(isActivate));

        if (isActivate) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new MessageResponse("Empleados no encontrados", null));
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(employeeResponseDtoList);
    }

    @GetMapping
    public ResponseEntity<?> getEmployeeById(@RequestParam("employeeId") Integer employeeId) {

        Employee employee = employeeService.getEmployeeById(employeeId);

        if(Objects.nonNull(employee)) {

            EmployeeResponseDto employeeResponseDto = EmployeeMapperDto.toModel(employee);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(employeeResponseDto);

        }

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new MessageResponse("Empleado no encontrado.", null));

    }

    @PostMapping()
    public ResponseEntity<?> createEmployee(@Valid @RequestBody EmployeeRequestDto employeeRequestDto) {

        User user = new User();
        Set<Role> roles = new HashSet<>();

        if (employeeRequestDto.getUsername() != null) {
            if (userService.existsByUserName(employeeRequestDto.getUsername())) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(new MessageResponse(
                                String.format("El Nombre de Usuario %s ya esta registrado.", employeeRequestDto.getUsername()), employeeRequestDto));
            }

            Set<String> strRoles = employeeRequestDto.getRoles();

            if (strRoles == null) {
                Role cashierRole = roleService.getRoleByName(ERole.ROLE_CASHIER);
                roles.add(cashierRole);
            } else {

                strRoles.forEach(role -> {
                    switch (role) {
                        case "admin":
                            Role adminRole = roleService.getRoleByName(ERole.ROLE_ADMIN);
                            roles.add(adminRole);
                            break;
                        case "manager":
                            Role managerRole = roleService.getRoleByName(ERole.ROLE_MANAGER);
                            roles.add(managerRole);
                            break;
                        default:
                            Role cashierRole = roleService.getRoleByName(ERole.ROLE_CASHIER);
                            roles.add(cashierRole);
                            break;
                    }
                });
            }

            user.setUsername(employeeRequestDto.getUsername());
            user.setPassword(encoder.encode(employeeRequestDto.getPassword()));
            user.setRoles(roles);
        }

        Employee employee = EmployeeMapperDto.toEntity(employeeRequestDto);
        employee.setUser(user);
        employee.setCreatedDateTime(new Date());
        employee.setUpdatedDateTime(new Date());
        employee.setIsActivate(true);

        employeeService.saveEmployee(employee);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new MessageResponse(
                        String.format("Empleado %s registrado con exito!", employee.getFirstName()), employee));
    }

    @PutMapping
    @RequestMapping("/updateEmployeeInfo")
    public ResponseEntity<?> updateEmployeeInfo(@Valid @RequestBody EmployeeRequestDto employeeRequestDto) {

        Employee employeeToUpdate = employeeService.getEmployeeById(employeeRequestDto.getEmployeeId());

        if (Objects.nonNull(employeeToUpdate)) {
            if (employeeService.existsBySsn(employeeToUpdate.getSsn())) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(new MessageResponse(
                                String.format("No se puede actualizar el empleado %s, el empleado ya se encuentra registrado", employeeRequestDto.getEmployeeId()),
                                employeeRequestDto));
            }

            employeeToUpdate = EmployeeMapperDto.toEntity(employeeRequestDto);
            employeeToUpdate.setUpdatedDateTime(new Date());

            employeeService.updateEmployee(employeeToUpdate);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new MessageResponse(
                    String.format("Empleado %s actualizado con exito!", employeeToUpdate.getFirstName()), employeeToUpdate));
        }

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new MessageResponse(
                        String.format("No existe el empleado %s.", employeeRequestDto.getFirstName()), employeeRequestDto));

    }

    @DeleteMapping
    public ResponseEntity<?> deleteEmployee(@Param("employeeId") Integer employeeId) {

        Employee employeeToDelete = employeeService.getEmployeeById(employeeId);

        if (Objects.nonNull(employeeToDelete)) {
            employeeService.deleteEmployee(employeeToDelete.getId());
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new MessageResponse("Empleado eliminado con exitoso!", employeeToDelete));
        } else {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new MessageResponse("No se encontro el empleado que desea borrar.", null));
        }
    }

}
