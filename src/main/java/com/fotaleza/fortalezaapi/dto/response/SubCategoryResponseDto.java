package com.fotaleza.fortalezaapi.dto.response;

import com.fotaleza.fortalezaapi.model.Category;
import lombok.Data;

import java.util.Date;

@Data
public class SubCategoryResponseDto {

    private Integer id;
    private String name;
    private String description;
    private Category category;
    private Date createdDateTime;
    private Date updatedDateTime;
    private Boolean isActivate;
}
