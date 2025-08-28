package com.fotaleza.fortalezaapi.dto.response;

import com.fotaleza.fortalezaapi.model.Role;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class EmployeeResponseDto {

    private Integer employeeId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String address;
    private String ssn;
    private String username;
    private List<Role> roles;
    private Date createdDateTime;
    private Date updatedDateTime;
    private Boolean isActivate;

}
