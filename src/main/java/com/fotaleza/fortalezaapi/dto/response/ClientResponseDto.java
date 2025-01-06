package com.fotaleza.fortalezaapi.dto.response;

import lombok.Data;

@Data
public class ClientResponseDto {

    private Integer id;
    private String companyName;
    private String firstName;
    private String lastName;
    private String address;
    private String email;
    private String phone;
    private String rfc;

}
