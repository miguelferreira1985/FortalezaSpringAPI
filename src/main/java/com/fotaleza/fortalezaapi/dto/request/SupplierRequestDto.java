package com.fotaleza.fortalezaapi.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SupplierRequestDto {

    private Integer id;

    @NotBlank
    @Size(min = 3, max = 50)
    private String name;

    @Size(min = 3, max = 50)
    private String contact;

    @Size(min = 3, max = 100)
    private String address;

    @Email
    @Size(min = 3, max = 50)
    private String email;

    @Size(min = 3, max = 20)
    private String phone;

}
