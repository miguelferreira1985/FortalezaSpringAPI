package com.fotaleza.fortalezaapi.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthRequestDTO {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

}
