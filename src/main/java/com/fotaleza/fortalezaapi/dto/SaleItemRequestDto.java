package com.fotaleza.fortalezaapi.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class SaleItemRequestDto {

    @NotNull(message = "El ID del producto no puede ser nulo.")
    private Integer productId;

    @NotNull(message = "La cantidad no puede ser nula.")
    @DecimalMin(value = "0.01", message = "La cantidad debe ser mayor a 0.")
    private BigDecimal quantity;

}
