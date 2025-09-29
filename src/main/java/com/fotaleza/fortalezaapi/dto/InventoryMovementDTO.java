package com.fotaleza.fortalezaapi.dto;

import com.fotaleza.fortalezaapi.enums.EMovementType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InventoryMovementDTO {

    private Integer id;
    private Integer productId;
    private String productName;
    private String productCode;
    private EMovementType movementType;
    private BigDecimal quantity;
    private BigDecimal previousStock;
    private BigDecimal newStock;
    private String createdBy;
    private LocalDateTime movementDate;

}
