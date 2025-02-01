package com.fotaleza.fortalezaapi.controller;

import com.fotaleza.fortalezaapi.dto.request.EmployeeRequestDto;
import com.fotaleza.fortalezaapi.dto.response.EmployeeResponseDto;
import com.fotaleza.fortalezaapi.dto.response.MessageResponse;
import com.fotaleza.fortalezaapi.model.ERole;
import com.fotaleza.fortalezaapi.model.Employee;
import com.fotaleza.fortalezaapi.model.Role;
import com.fotaleza.fortalezaapi.model.User;
import com.fotaleza.fortalezaapi.service.impl.EmployeeServiceImpl;
import com.fotaleza.fortalezaapi.service.impl.RoleServiceImpl;
import com.fotaleza.fortalezaapi.service.impl.UserServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/v1/employee")
public class EmployeeController {

    @Autowired
    private EmployeeServiceImpl employeeService;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private RoleServiceImpl roleService;

    @Autowired
    PasswordEncoder encoder;

    @GetMapping
    @RequestMapping("/getAllActiveEmployees")
    public List<Employee> getAllActiveEmployees() {
        return employeeService.getAllActiveEmployees();
    }

    @GetMapping
    @RequestMapping("/getAllInactiveEmployees")
    public List<Employee> getAllInactiveEmployees() {
        return employeeService.getAllInactivateEmployees();
    }

    @GetMapping
    public ResponseEntity<?> getEmployeeById(@RequestParam("employeeId") Integer employeeId) {

        Employee employee = employeeService.getEmployeeById(employeeId);

        if(Objects.nonNull(employee)) {

            EmployeeResponseDto employeeResponseDto = new EmployeeResponseDto();
            employeeResponseDto.setEmployeeId(employee.getId());
            employeeResponseDto.setFirstName(employee.getFirstName());
            employeeResponseDto.setLastName(employee.getLastName());
            employeeResponseDto.setEmail(employee.getEmail());
            employeeResponseDto.setPhone(employee.getPhone());
            employeeResponseDto.setAddress(employee.getAddress());
            employeeResponseDto.setSsn(employeeResponseDto.getSsn());
            employeeResponseDto.setUsername(employee.getUser().getUsername());
            employeeResponseDto.setRoles(employee.getUser().getRoles().stream().toList());
            employeeResponseDto.setCreatedDateTime(employee.getCreatedDateTime());
            employeeResponseDto.setUpdatedDateTime(employee.getUpdatedDateTime());
            employeeResponseDto.setIsActivate(employee.getIsActivate());

            return ResponseEntity.ok(employeeResponseDto);

        }

        return ResponseEntity.notFound().build();

    }

    @PostMapping()
    public ResponseEntity<?> createEmployee(@Valid @RequestBody EmployeeRequestDto employeeRequestDto) {

        User user = new User();
        Set<Role> roles = new HashSet<>();

        if (employeeRequestDto.getUsername() != null) {
            if (userService.existsByUserName(employeeRequestDto.getUsername())) {
                return ResponseEntity.badRequest().body(new MessageResponse("El Nombre de Usuario ya esta registrado", employeeRequestDto.getUsername()));
            }

            Set<String> strRoles = employeeRequestDto.getRole();

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

        Employee employee = new Employee();
        employee.setFirstName(employeeRequestDto.getFirstName());
        employee.setLastName(employeeRequestDto.getLastName());
        employee.setEmail(employeeRequestDto.getEmail());
        employee.setPhone(employeeRequestDto.getPhone());
        employee.setAddress(employeeRequestDto.getAddress());
        employee.setSsn(employeeRequestDto.getSsn());
        employee.setUser(user);
        employee.setCreatedDateTime(new Date());
        employee.setUpdatedDateTime(new Date());
        employee.setIsActivate(true);

        employeeService.saveEmployee(employee);

        return ResponseEntity.ok(new MessageResponse("Empleado " + employee.getFirstName() + " registrado con exito!", employee));
    }

    @PutMapping
    @RequestMapping("/updateEmployeeInfo")
    public ResponseEntity<?> updateEmployeeInfo(@Valid @RequestBody EmployeeRequestDto employeeRequestDto) {

        Employee employeeToUpdate = employeeService.getEmployeeById(employeeRequestDto.getEmployeeId());

        if (Objects.nonNull(employeeToUpdate)) {
            if (employeeService.existsBySsn(employeeToUpdate.getSsn())) {
                return ResponseEntity.badRequest()
                        .body(new MessageResponse(
                                String.format("No se puede actualizar el empleado %s, el empleado ya se encuentra registrado", employeeRequestDto.getEmployeeId()),
                                employeeRequestDto));
            }

            employeeToUpdate.setFirstName(employeeRequestDto.getFirstName());
            employeeToUpdate.setLastName(employeeRequestDto.getLastName());
            employeeToUpdate.setEmail(employeeRequestDto.getEmail());
            employeeToUpdate.setPhone(employeeRequestDto.getPhone());
            employeeToUpdate.setAddress(employeeRequestDto.getAddress());
            employeeToUpdate.setSsn(employeeRequestDto.getSsn());
            employeeToUpdate.setUpdatedDateTime(new Date());

            employeeService.updateEmployee(employeeToUpdate);

            return ResponseEntity.ok(new MessageResponse(
                    String.format("Cliente %s agregado con exito!", employeeToUpdate.getFirstName()), employeeToUpdate));
        }

        return ResponseEntity.badRequest()
                .body(new MessageResponse(
                        String.format("No existe el empleado %s.", employeeRequestDto.getFirstName()), employeeRequestDto));

    }

    @DeleteMapping
    public ResponseEntity<?> deleteEmployee(@Param("employeeId") Integer employeeId) {

        Employee employeeToDelete = employeeService.getEmployeeById(employeeId);

        if (Objects.nonNull(employeeToDelete)) {
            if (!employeeToDelete.getIsActivate()) {
                return ResponseEntity.ok(employeeToDelete);
            }

            employeeService.deleteEmployee(employeeToDelete.getId());

            return ResponseEntity.ok(new MessageResponse("Empleado eliminado con exitoso!", employeeToDelete));
        }

        return ResponseEntity.badRequest()
                .body(new MessageResponse("No se encontro el empleado que desea borrar.", null));

    }

}
