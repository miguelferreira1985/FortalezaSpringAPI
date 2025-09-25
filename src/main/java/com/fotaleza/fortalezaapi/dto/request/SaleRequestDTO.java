package com.fotaleza.fortalezaapi.dto.request;

import com.fotaleza.fortalezaapi.enums.PaymentType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class SaleRequestDTO {

    @NotNull(message = "El tipo de pago no puede ser nulo.")
    private PaymentType paymentType;

    @NotEmpty(message = "La vensta de be contener al menos un articulo.")
    @Valid
    private Set<SaleItemRequestDto> saleItems;

}
