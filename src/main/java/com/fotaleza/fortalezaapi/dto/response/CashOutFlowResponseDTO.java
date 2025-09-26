package com.fotaleza.fortalezaapi.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class CashOutFlowResponseDTO {

    private Integer id;
    private BigDecimal amount;
    private String reason;
    private Integer cashStartId;
    private Boolean isActivate;

    private LocalDateTime createdDateTime;
    private LocalDateTime updatedDateTime;
    private String createdBy;
    private String updatedBy;

}
