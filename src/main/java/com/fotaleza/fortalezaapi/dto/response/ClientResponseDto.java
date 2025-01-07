package com.fotaleza.fortalezaapi.dto.response;

import com.fotaleza.fortalezaapi.constants.ColumnNames;
import jakarta.persistence.Column;
import lombok.Data;

import java.util.Date;

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
    private Date createdDateTime;
    private Date updatedDateTime;

}
