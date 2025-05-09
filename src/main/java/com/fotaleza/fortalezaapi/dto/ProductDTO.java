package com.fotaleza.fortalezaapi.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.Set;

@Data
public class ProductDTO {

    private Integer id;

    @NotBlank
    @Size(max = 50)
    private String name;

    @NotBlank
    @Size(max = 20)
    private String code;

    private String description;

    @NotNull
    @DecimalMin("0.0")
    private Double price;

    @NotNull
    @DecimalMin("0.0")
    private Double cost;

    @NotNull
    @Min(0)
    private Double stock;

    @NotNull
    @Min(0)
    private Double minimumStock;

    private Integer subcategoryId;
    private Set<Integer> supplierIds;
    private Boolean isActivate;
}
