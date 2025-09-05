package com.fotaleza.fortalezaapi.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class SaleItemResponseDTO {

    private Integer id;
    private Integer productId;
    private String productName;
    private String productCode;
    private BigDecimal quantity;
    private BigDecimal unitPrice;
    private BigDecimal subtotal;

}
