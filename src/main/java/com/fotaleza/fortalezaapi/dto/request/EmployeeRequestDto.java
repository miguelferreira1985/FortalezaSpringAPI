package com.fotaleza.fortalezaapi.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class EmployeeRequestDto {

    @NotBlank
    @Size(min = 3, max = 20)
    private String username;

    @NotBlank
    @Size(min = 3, max = 50)
    private String firstName;

    @NotBlank
    @Size(min = 3, max = 50)
    private String lastName;

    @Size(min = 3, max = 50)
    private String email;

    @Size(min = 3, max = 20)
    private String phone;

    @Size(min = 3, max = 100)
    private String address;

    @NotBlank
    @Size(min = 3, max = 20)
    private String ssn;

    @NotBlank
    @Size(min = 6, max = 40)
    private String password;

    private Set<String> role;

}
