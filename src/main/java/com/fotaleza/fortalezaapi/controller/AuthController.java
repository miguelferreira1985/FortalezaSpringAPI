package com.fotaleza.fortalezaapi.controller;

import com.fotaleza.fortalezaapi.dto.response.ApiResponse;
import com.fotaleza.fortalezaapi.dto.response.AuthResponseDTO;
import com.fotaleza.fortalezaapi.dto.request.AuthRequestDTO;
import com.fotaleza.fortalezaapi.security.jwt.JwtUtils;
import com.fotaleza.fortalezaapi.security.service.UserDetailsImpl;
import com.fotaleza.fortalezaapi.service.impl.UserServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponseDTO>> authenticateUser(@Valid @RequestBody AuthRequestDTO authRequestDto) {

        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(
                            authRequestDto.getUsername(),
                            authRequestDto.getPassword()
                    ));

            UserDetailsImpl userDetails = UserDetailsImpl.build(userService.getByUserName(authRequestDto.getUsername()));

            List<String> roles = userDetails.getAuthorities()
                    .stream().map(GrantedAuthority::getAuthority)
                    .toList();

            String jwt = jwtUtils.generateToken(userDetails, roles);
            String refreshToken = jwtUtils.generateRefreshToken(userDetails, roles);

            userService.resetFailedAttempts(authRequestDto.getUsername());

            AuthResponseDTO authResponseDto = new AuthResponseDTO();
            authResponseDto.setToken(jwt);
            authResponseDto.setRefreshToken(refreshToken);
            //authResponseDto.setUsername(userDetails.getUsername());
            //authResponseDto.setRoles(roles);

            return ResponseEntity.ok(
                    ApiResponse.<AuthResponseDTO>builder()
                            .status(HttpStatus.OK.value())
                            .message("Autenticación exitosa.")
                            .data(authResponseDto)
                            .timestamp(LocalDateTime.now())
                            .build()
            );
        } catch (BadCredentialsException ex) {
            userService.processFailedLogin(authRequestDto.getUsername());
            throw ex;
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<AuthResponseDTO>> refreshUserToken(@RequestBody Map<String, String> request) {

        String refreshToken = request.get("refreshToken");
        String username = jwtUtils.getUserNamefromJwtToken(refreshToken);
        UserDetailsImpl userDetails = UserDetailsImpl.build(userService.getByUserName(username));
        List<String> roles = userDetails.getAuthorities()
                .stream().map(GrantedAuthority::getAuthority)
                .toList();

        if (!jwtUtils.validateJwtToken(refreshToken, userDetails)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    ApiResponse.<AuthResponseDTO>builder()
                            .status(HttpStatus.UNAUTHORIZED.value())
                            .message("Token para refrescar inválido.")
                            .timestamp(LocalDateTime.now())
                            .build()
            );
        }

        String newJwt = jwtUtils.generateToken(userDetails, roles);
        String newRefreshToken = jwtUtils.generateRefreshToken(userDetails, roles);

        AuthResponseDTO authResponseDto = new AuthResponseDTO();
        authResponseDto.setToken(newJwt);
        authResponseDto.setRefreshToken(newRefreshToken);
        //authResponseDto.setUsername(userDetails.getUsername());
        //authResponseDto.setRoles(roles);

        return ResponseEntity.ok(
                ApiResponse.<AuthResponseDTO>builder()
                        .status(HttpStatus.OK.value())
                        .message("Token refrescado exitosamente.")
                        .data(authResponseDto)
                        .timestamp(LocalDateTime.now())
                        .build()
        );

    }
}
