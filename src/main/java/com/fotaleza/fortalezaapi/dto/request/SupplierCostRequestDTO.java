package com.fotaleza.fortalezaapi.dto.request;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SupplierCostRequestDTO {
    private Integer supplierId;
    private BigDecimal cost;
    private BigDecimal discount;
}
