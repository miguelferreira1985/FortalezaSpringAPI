package com.fotaleza.fortalezaapi.dto.response;

import com.fotaleza.fortalezaapi.dto.PurchaseOrderItemDTO;
import com.fotaleza.fortalezaapi.enums.EPurchaseOrderStatus;
import jdk.dynalink.linker.LinkerServices;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseOrderResponseDTO {
    private Integer id;
    private String supplierName;
    private EPurchaseOrderStatus orderStatus;
    private LocalDateTime orderDate;
    private LocalDateTime expectedDeliveryDate;
    private BigDecimal totalAmount;
    private List<PurchaseOrderItemDTO> items;
}
