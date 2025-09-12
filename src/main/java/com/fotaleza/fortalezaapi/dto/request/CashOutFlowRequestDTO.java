package com.fotaleza.fortalezaapi.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CashOutFlowRequestDTO {

    @NotNull(message = "El monto no puede ser nulo.")
    @DecimalMin(value = "0.01", message = "El monto debe ser mayor a 0.")
    private BigDecimal amount;

    @NotBlank(message = "La razon no puede estar en blanco")
    private String reason;

}
