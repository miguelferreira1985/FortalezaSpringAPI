package com.fotaleza.fortalezaapi.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {

    private Integer id;

    @NotBlank(message = "El nombre no puede estar en blanco.")
    @Size(max = 50, message = "El nombre no puede exceder los 50 caracteres.")
    private String name;

    @NotBlank(message = "El código no puede estar en blanco.")
    @Size(max = 20, message = "El código no puede exceder los 20 caracteres.")
    private String code;

    private String description;

    @NotNull(message = "El precio no puede ser nulo.")
    @DecimalMin(value = "0.0", message = "El precio debe ser mayor o igual a 0.")
    private BigDecimal price;

    @NotNull(message = "El costo no puede ser nulo.")
    @DecimalMin(value = "0.0", message = "El costo debe ser mayor o igual a 0.")
    private BigDecimal cost;

    @NotNull(message = "El stock no puede ser nulo.")
    @DecimalMin(value = "0.0", message = "El stock debe ser mayor o igual a 0.")
    private BigDecimal stock;

    @NotNull(message = "El stock mínimo no puede ser nulo.")
    @DecimalMin(value = "0.0", message = "El stock mínimo debe ser mayor o igual a 0.")
    private BigDecimal minimumStock;

    private Integer subcategoryId;
    private Set<Integer> supplierIds;
    private Boolean isActivate;

    private BigDecimal profitMargin;
    private BigDecimal profitValue;
    private BigDecimal inventoryValue;
    private Boolean isBelowOrEqualMinimumStock;
}
