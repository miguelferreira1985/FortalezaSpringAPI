package com.fotaleza.fortalezaapi.controller;

import com.fotaleza.fortalezaapi.dto.response.AuthResponseDto;
import com.fotaleza.fortalezaapi.model.ERole;
import com.fotaleza.fortalezaapi.model.Employee;
import com.fotaleza.fortalezaapi.model.Role;
import com.fotaleza.fortalezaapi.model.User;
import com.fotaleza.fortalezaapi.dto.request.AuthRequestDto;
import com.fotaleza.fortalezaapi.dto.request.SignupRequestDto;
import com.fotaleza.fortalezaapi.dto.response.MessageResponse;
import com.fotaleza.fortalezaapi.security.jwt.JwtUtils;
import com.fotaleza.fortalezaapi.security.service.UserDetailsImpl;
import com.fotaleza.fortalezaapi.security.service.UserDetailsServiceImpl;
import com.fotaleza.fortalezaapi.service.impl.RoleServiceImpl;
import com.fotaleza.fortalezaapi.service.impl.UserServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserServiceImpl userService;

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    RoleServiceImpl roleService;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody AuthRequestDto authRequestDto) {

        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(authRequestDto.getUsername(), authRequestDto.getPassword()));

            UserDetailsImpl userDetails = UserDetailsImpl.build(userService.getByUserName(authRequestDto.getUsername()));

            List<String> roles = userDetails.getAuthorities()
                    .stream().map(GrantedAuthority::getAuthority)
                    .toList();

            String jwt = jwtUtils.generateToken(userDetails, roles);
            String refreshToken = jwtUtils.generateRefreshToken(userDetails, roles);

            AuthResponseDto authResponseDto = new AuthResponseDto();
            authResponseDto.setToken(jwt);
            authResponseDto.setRefreshToken(refreshToken);

            return new ResponseEntity<AuthResponseDto>(authResponseDto, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication error" + e.getMessage());
        }

    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshUserToken(@RequestBody Map<String, String> request) {

        String refreshToken = request.get("refreshToken");

        try {

            String username = jwtUtils.getUserNamefromJwtToken(refreshToken);
            UserDetailsImpl userDetails = UserDetailsImpl.build(userService.getByUserName(username));
            List<String> roles = userDetails.getAuthorities()
                    .stream().map(GrantedAuthority::getAuthority)
                    .toList();

            if (jwtUtils.validateJwtToken(refreshToken, userDetails)) {

                String newJwt = jwtUtils.generateToken(userDetails, roles);
                String newRefreshToken = jwtUtils.generateRefreshToken(userDetails, roles);

                AuthResponseDto authResponseDto = new AuthResponseDto();
                authResponseDto.setToken(newJwt);
                authResponseDto.setRefreshToken(newRefreshToken);

                return new ResponseEntity<AuthResponseDto>(authResponseDto, HttpStatus.OK);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Refresh Token");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Error refresh token" + e.getMessage());
        }

    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequestDto signupRequestDto) {

        if (userService.existsByUserName(signupRequestDto.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("El Nombre de Usuario ya esta registrado", signupRequestDto.getUsername()));
        }

        Set<String> strRoles = signupRequestDto.getRole();
        Set<Role> roles = new HashSet<>();

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

        Employee employee = new Employee();
        employee.setFirstName(signupRequestDto.getFirstName());
        employee.setLastName(signupRequestDto.getLastName());
        employee.setSsn("4667883");

        User user = new User();
        user.setUsername(signupRequestDto.getUsername());
        //user.setEmployee(employee);
        user.setPassword(encoder.encode(signupRequestDto.getPassword()));
        user.setRoles(roles);

        employee.setUser(user);

        userService.saveUser(user);

        return ResponseEntity.ok(new MessageResponse("Usuario " + user.getUsername() + " registrado con exito!", user));
    }

}
