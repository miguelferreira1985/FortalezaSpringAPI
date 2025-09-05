package com.fotaleza.fortalezaapi.dto;

import com.fotaleza.fortalezaapi.enums.PaymentType;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
public class SaleResponseDTO {

    private Integer id;
    private PaymentType paymentType;
    private BigDecimal totalAmount;
    private Set<SaleItemResponseDTO> saleItems;
    private Boolean isActivate;

    private LocalDateTime createdDateTime;
    private LocalDateTime updatedDateTime;
    private String createdBy;
    private String updatedBy;

}
