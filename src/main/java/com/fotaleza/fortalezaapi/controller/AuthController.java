package com.fotaleza.fortalezaapi.controller;

import com.fotaleza.fortalezaapi.dto.UserDataDTO;
import com.fotaleza.fortalezaapi.dto.response.AuthResponseDTO;
import com.fotaleza.fortalezaapi.dto.request.AuthRequestDTO;
import com.fotaleza.fortalezaapi.model.User;
import com.fotaleza.fortalezaapi.security.jwt.JwtUtils;
import com.fotaleza.fortalezaapi.security.service.UserDetailsImpl;
import com.fotaleza.fortalezaapi.service.impl.UserServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserServiceImpl userService;
    private final JwtUtils jwtUtils;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, UserServiceImpl userService, JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/register")
    public ResponseEntity<UserDataDTO> registerUser(@Valid @RequestBody UserDataDTO userDataDTO) {
        User user = userService.createUser(
                userDataDTO.getUsername(),
                userDataDTO.getPassword(),
                userDataDTO.getRoles()
        );

        return ResponseEntity.ok(userDataDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody AuthRequestDTO authRequestDto) {

        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(authRequestDto.getUsername(), authRequestDto.getPassword()));

            UserDetailsImpl userDetails = UserDetailsImpl.build(userService.getByUserName(authRequestDto.getUsername()));

            List<String> roles = userDetails.getAuthorities()
                    .stream().map(GrantedAuthority::getAuthority)
                    .toList();

            String jwt = jwtUtils.generateToken(userDetails, roles);
            String refreshToken = jwtUtils.generateRefreshToken(userDetails, roles);

            AuthResponseDTO authResponseDto = new AuthResponseDTO();
            authResponseDto.setToken(jwt);
            authResponseDto.setRefreshToken(refreshToken);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(authResponseDto);

        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(String.format("Error de autenticación: %s", e.getMessage()));
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

                AuthResponseDTO authResponseDto = new AuthResponseDTO();
                authResponseDto.setToken(newJwt);
                authResponseDto.setRefreshToken(newRefreshToken);

                return ResponseEntity
                        .status(HttpStatus.OK)
                        .body(authResponseDto);
            } else {
                return ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED)
                        .body("Token para refrescar inválido.");
            }
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(String.format("Error en token para refrescar: %s", e.getMessage()));
        }

    }
}
