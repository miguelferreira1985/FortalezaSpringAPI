package com.fotaleza.fortalezaapi.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CashCutRequestDTO {

    @NotNull(message = "El monto final no puede ser nulo.")
    @DecimalMin(value = "0.0", message = "el monto final debe ser mayor o igual a 0.")
    private BigDecimal finalAmount;

}
