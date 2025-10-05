package com.fotaleza.fortalezaapi.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDTO {

    private Integer id;

    @NotBlank(message = "Username es requerido.")
    @Size(max = 20, message = "Username must not exceed 20 characters.")
    private String username;

    @NotBlank(message = "Password is required.")
    @Size(max = 120, message = "Password must not exceed 120 characters.")
    private String password;

    private Set<String> roles;
}
