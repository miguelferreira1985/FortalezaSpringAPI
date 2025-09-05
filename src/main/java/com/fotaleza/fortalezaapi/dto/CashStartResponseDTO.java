package com.fotaleza.fortalezaapi.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class CashStartResponseDTO {

    private Integer id;
    private BigDecimal startAmount;
    private LocalDateTime endDateTime;
    private BigDecimal finalAmount;
    private BigDecimal difference;
    private Boolean isActivate;

    private LocalDateTime createdDateTime;
    private LocalDateTime updatedDateTime;
    private String createdBy;
    private String updatedBy;

}
