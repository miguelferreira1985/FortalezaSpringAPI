package com.fotaleza.fortalezaapi.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CashStartRequestDTO {

    @NotNull(message = "El monto inicial no puede ser nulo.")
    @DecimalMin(value = "0.0", message = "El monto inicial debe ser mayor o igual a 0.")
    private BigDecimal startAmount;

}
