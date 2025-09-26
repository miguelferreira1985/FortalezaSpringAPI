package com.fotaleza.fortalezaapi.dto;

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
public class CashCutReportDTO {

    // Informacion del CashStart
    private Integer cashStartId;
    private BigDecimal startAmount;
    private LocalDateTime startDateTime;

    //Informacion del CashCut
    private BigDecimal finalAmount;
    private BigDecimal expectedCashAmount;
    private BigDecimal cashDifference;
    private LocalDateTime cutDateTime;

    // Totales de ventas por tipo de pago
    private BigDecimal totalCashSales;
    private BigDecimal totalCardSales;
    private BigDecimal totalTransferSales;

    // Total de salidas de caja
    private BigDecimal totalOutFlows;

    private Boolean isActivate;

    private LocalDateTime createdDateTime;
    private LocalDateTime updatedDateTime;
    private String createdBy;
    private String updatedBy;

}
