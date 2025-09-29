package com.fotaleza.fortalezaapi.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class StockUpdateRequestDTO {

    @NotNull(message = "La cantidad es obligarotia")
    private BigDecimal quantity; // puede ser positiva o negativa

}
