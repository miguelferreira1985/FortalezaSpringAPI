package com.fotaleza.fortalezaapi.dto.response;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SupplierCostResponseDTO {
    private Integer supplierId;
    private String supplierName;
    private BigDecimal cost;
    private BigDecimal discount;
}
