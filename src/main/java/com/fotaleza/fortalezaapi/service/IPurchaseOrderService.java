package com.fotaleza.fortalezaapi.service;

import com.fotaleza.fortalezaapi.dto.request.PurchaseOrderRequestDTO;
import com.fotaleza.fortalezaapi.dto.response.PurchaseOrderResponseDTO;

import java.util.List;

public interface IPurchaseOrderService {

    PurchaseOrderResponseDTO createPurchaseOrder(PurchaseOrderRequestDTO purchaseOrderRequestDTO);
    PurchaseOrderResponseDTO getPurchaseOrderById(Integer purchaseOrderId);
    List<PurchaseOrderResponseDTO> getAllPurchaseOrders();
    List<PurchaseOrderResponseDTO> getAllPurchaseOrdersBySupplier(Integer supplierId);
    PurchaseOrderResponseDTO updateOrderStatus(Integer purchaseOrderId, String status);

}
