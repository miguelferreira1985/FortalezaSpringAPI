package com.fotaleza.fortalezaapi.mapper;

import com.fotaleza.fortalezaapi.dto.PurchaseOrderItemDTO;
import com.fotaleza.fortalezaapi.dto.response.PurchaseOrderResponseDTO;
import com.fotaleza.fortalezaapi.model.PurchaseOrder;
import com.fotaleza.fortalezaapi.model.PurchaseOrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PurchaseOrderMapper {

    @Mapping(target = "supplierName", source = "supplier.name")
    PurchaseOrderResponseDTO toResponseDTO(PurchaseOrder purchaseOrder);

    List<PurchaseOrderResponseDTO> toResponseDTOList(List<PurchaseOrder> purchaseOrders);

    @Mapping(target = "productName", source = "product.name")
    PurchaseOrderItemDTO toItemDTO(PurchaseOrderItem item);

    List<PurchaseOrderItemDTO> toItemDTOList(List<PurchaseOrderItem> items);


}
