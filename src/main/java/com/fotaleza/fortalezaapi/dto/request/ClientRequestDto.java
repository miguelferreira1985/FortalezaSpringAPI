package com.fotaleza.fortalezaapi.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientRequestDto {

    private Integer id;

    @Size(min = 3, max = 100)
    private String companyName;

    @NotBlank
    @Size(min = 3, max = 50)
    private String firstName;

    @NotBlank
    @Size(min = 3, max = 50)
    private String lastName;

    @Size(min = 3, max = 100)
    private String address;

    @Email
    @Size(min = 3, max = 50)
    private String email;

    @Size(min = 3, max = 20)
    private String phone;

    @NotBlank
    @Size(min = 3, max = 20)
    private String rfc;
}
