package com.fotaleza.fortalezaapi.service.impl;

import com.fotaleza.fortalezaapi.dto.PurchaseOrderItemDTO;
import com.fotaleza.fortalezaapi.dto.request.PurchaseOrderItemRequestDTO;
import com.fotaleza.fortalezaapi.dto.request.PurchaseOrderRequestDTO;
import com.fotaleza.fortalezaapi.dto.response.PurchaseOrderResponseDTO;
import com.fotaleza.fortalezaapi.enums.EPurchaseOrderStatus;
import com.fotaleza.fortalezaapi.exception.ResourceNotFoundException;
import com.fotaleza.fortalezaapi.mapper.PurchaseOrderMapper;
import com.fotaleza.fortalezaapi.model.Product;
import com.fotaleza.fortalezaapi.model.PurchaseOrder;
import com.fotaleza.fortalezaapi.model.PurchaseOrderItem;
import com.fotaleza.fortalezaapi.model.Supplier;
import com.fotaleza.fortalezaapi.repository.ProductRepository;
import com.fotaleza.fortalezaapi.repository.PurchaseOrderItemRepository;
import com.fotaleza.fortalezaapi.repository.PurchaseOrderRepository;
import com.fotaleza.fortalezaapi.repository.SupplierRepository;
import com.fotaleza.fortalezaapi.service.IPurchaseOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PurchaseOrderServiceImpl implements IPurchaseOrderService {

    private final PurchaseOrderRepository purchaseOrderRepository;
    private final PurchaseOrderItemRepository purchaseOrderItemRepository;
    private final SupplierRepository supplierRepository;
    private final ProductRepository productRepository;
    private final PurchaseOrderMapper purchaseOrderMapper;

    @Override
    @Transactional
    public PurchaseOrderResponseDTO createPurchaseOrder(PurchaseOrderRequestDTO purchaseOrderRequestDTO) {

        Supplier supplier = supplierRepository.findById(purchaseOrderRequestDTO.getSupplierId())
                .orElseThrow(() -> new ResourceNotFoundException("El proveedor no existe."));

        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setSupplier(supplier);
        purchaseOrder.setOrderDate(LocalDateTime.now());
        purchaseOrder.setExpectedDeliveryDate(purchaseOrderRequestDTO.getExpectedDeliveryDate());
        purchaseOrder.setOrderStatus(EPurchaseOrderStatus.PENDING);

        PurchaseOrder savedOrder = purchaseOrderRepository.save(purchaseOrder);

        BigDecimal totalAmount = BigDecimal.ZERO;
        List<PurchaseOrderItem> items = new ArrayList<>();

        for (PurchaseOrderItemDTO itemDTO : purchaseOrderRequestDTO.getItems()) {

            Product product = productRepository.findById(itemDTO.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado."));

            PurchaseOrderItem item = new PurchaseOrderItem();
            item.setPurchaseOrder(savedOrder);
            item.setProduct(product);
            item.setQuantityOrdered(itemDTO.getQuantityOrdered());
            item.setQuantityReceived(BigDecimal.ZERO);
            item.setUnitCost(itemDTO.getUnitCost());
            item.setSubtotal(itemDTO.getQuantityOrdered().multiply(itemDTO.getUnitCost()));

            totalAmount = totalAmount.add(item.getSubtotal());
            items.add(item);
        }

        purchaseOrderItemRepository.saveAll(items);

        savedOrder.setTotalAmount(totalAmount);
        purchaseOrderRepository.save(savedOrder);

        return purchaseOrderMapper.toResponseDTO(savedOrder);
    }

    @Override
    public PurchaseOrderResponseDTO getPurchaseOrderById(Integer purchaseOrderId) {
        PurchaseOrder order = purchaseOrderRepository.findById(purchaseOrderId)
                .orElseThrow(() -> new ResourceNotFoundException("La orden de compra no existe."));
        return purchaseOrderMapper.toResponseDTO(order);
    }

    @Override
    public List<PurchaseOrderResponseDTO> getAllPurchaseOrders() {
        return purchaseOrderMapper.toResponseDTOList(purchaseOrderRepository.findAll());
    }

    @Override
    public List<PurchaseOrderResponseDTO> getAllPurchaseOrdersBySupplier(Integer supplierId) {
        return purchaseOrderMapper.toResponseDTOList(purchaseOrderRepository.findBySupplierId(supplierId));
    }

    @Override
    @Transactional
    public PurchaseOrderResponseDTO updateOrderStatus(Integer purchaseOrderId, String status) {
        PurchaseOrder order = purchaseOrderRepository.findById(purchaseOrderId)
                .orElseThrow(() -> new ResourceNotFoundException("La orden de compra no existe."));

        order.setOrderStatus(EPurchaseOrderStatus.valueOf(status));
        PurchaseOrder updatedOrder = purchaseOrderRepository.save(order);

        return  purchaseOrderMapper.toResponseDTO(updatedOrder);
    }


}
