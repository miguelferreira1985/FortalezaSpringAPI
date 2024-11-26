package com.fotaleza.fortalezaapi.controller;

import com.fotaleza.fortalezaapi.model.ERole;
import com.fotaleza.fortalezaapi.model.Role;
import com.fotaleza.fortalezaapi.model.User;
import com.fotaleza.fortalezaapi.payload.request.LoginRequest;
import com.fotaleza.fortalezaapi.payload.request.SignupRequest;
import com.fotaleza.fortalezaapi.payload.response.MessageResponse;
import com.fotaleza.fortalezaapi.payload.response.UserInfoResponse;
import com.fotaleza.fortalezaapi.repository.RoleRepository;
import com.fotaleza.fortalezaapi.repository.UserRepository;
import com.fotaleza.fortalezaapi.security.jwt.JwtUtils;
import com.fotaleza.fortalezaapi.security.service.UserDetailsImpl;
import com.fotaleza.fortalezaapi.service.impl.RoleServiceImpl;
import com.fotaleza.fortalezaapi.service.impl.UserServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

// for Angular Client (withCredentials)
@CrossOrigin(origins = "http://localhost:8081", maxAge = 3600, allowCredentials = "true")
//@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserServiceImpl userService;

    @Autowired
    RoleServiceImpl roleService;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);

        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .body(new UserInfoResponse(userDetails.getId(),
                        userDetails.getUsername(),
                        userDetails.getFirstName(),
                        userDetails.getLastName(),
                        roles));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {

        if (userService.existsByUserName(signUpRequest.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: El Nombre de Usuario ya esta registrado", userService));
        }

        Set<String> strRoles = signUpRequest.getRole();
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

        User user = new User();
        user.setUsername(signUpRequest.getUsername());
        user.setFirstName(signUpRequest.getFirstName());
        user.setLastName(signUpRequest.getLastName());
        user.setPassword(encoder.encode(signUpRequest.getPassword()));
        user.setRoles(roles);

        userService.saveUser(user);

        return ResponseEntity.ok(new MessageResponse("Usuario " + user.getUsername() + " registrado con exito!", userService));
    }

    @PostMapping("/signout")
    public ResponseEntity<?> logoutUser() {
        ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
        return ResponseEntity.ok(new MessageResponse("Su sesion ha sido cerrada!", cookie));
    }

}
