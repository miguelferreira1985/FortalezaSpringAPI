package com.fotaleza.fortalezaapi.dto.response;

import lombok.Data;

import java.util.Date;

@Data
public class ColorResponseDto {

    private Integer id;
    private String name;
    private Date createdDateTime;
    private Date updatedDateTime;

}
