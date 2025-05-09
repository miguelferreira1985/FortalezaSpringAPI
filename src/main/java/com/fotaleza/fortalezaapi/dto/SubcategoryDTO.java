package com.fotaleza.fortalezaapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Data
public class SubcategoryDTO {

    private Integer id;

    @NotBlank
    @Size(max = 50)
    private String name;

    private String description;
    private Integer categoryId;
    private Set<Integer> productIds;
    private Boolean isActivate;
}
