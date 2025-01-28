package com.fotaleza.fortalezaapi.controller;

import com.fotaleza.fortalezaapi.dto.request.EmployeeRequestDto;
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
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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

}
