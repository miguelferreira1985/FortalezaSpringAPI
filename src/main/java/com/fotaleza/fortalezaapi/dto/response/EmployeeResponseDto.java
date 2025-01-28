package com.fotaleza.fortalezaapi.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class EmployeeResponseDto {

    private Long employeeId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String address;
    private String ssn;
    private String username;
    private List<String> roles;
    private Boolean isActivate;

}
