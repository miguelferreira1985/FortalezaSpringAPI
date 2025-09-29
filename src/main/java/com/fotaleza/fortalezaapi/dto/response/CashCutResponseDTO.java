package com.fotaleza.fortalezaapi.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class CashCutResponseDTO {

    private Integer id;
    private Integer cashStartId; // The ID of the cash shift that was cut
    private BigDecimal startAmount;
    private BigDecimal finalAmount;
    private BigDecimal difference;

    private LocalDateTime createdDateTime;
    private LocalDateTime updatedDateTime;
    private String createdBy;
    private String updatedBy;

}
