package com.fotaleza.fortalezaapi.dto.request;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseOrderItemRequestDTO {
    private Integer productId;
    private BigDecimal quantityOrdered;
    private BigDecimal unitCost;
}
