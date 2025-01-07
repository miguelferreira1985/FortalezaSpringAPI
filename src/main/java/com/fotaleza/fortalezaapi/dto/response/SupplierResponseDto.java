package com.fotaleza.fortalezaapi.dto.response;

import lombok.Data;

import java.util.Date;

@Data
public class SupplierResponseDto {

    private Integer id;
    private String name;
    private String contact;
    private String address;
    private String email;
    private String phone;
    private Date createdDateTime;
    private Date updatedDateTime;

}
