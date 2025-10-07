package com.fotaleza.fortalezaapi.controller;

import com.fotaleza.fortalezaapi.dto.request.PurchaseOrderRequestDTO;
import com.fotaleza.fortalezaapi.dto.response.ApiResponse;
import com.fotaleza.fortalezaapi.dto.response.PurchaseOrderResponseDTO;
import com.fotaleza.fortalezaapi.service.IPurchaseOrderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/purchase-orders")
@RequiredArgsConstructor
@Tag(name = "Purchase Orders", description = "Endpoints para gestión de órdenes de compra")
public class PurchaseOrderController {

    private final IPurchaseOrderService purchaseOrderService;

    @PostMapping
    public ResponseEntity<ApiResponse<PurchaseOrderResponseDTO>> createPurchaseOrder(@Valid @RequestBody PurchaseOrderRequestDTO purchaseOrderRequestDTO) {

        PurchaseOrderResponseDTO orderCreated = purchaseOrderService.createPurchaseOrder(purchaseOrderRequestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.<PurchaseOrderResponseDTO>builder()
                        .status(HttpStatus.CREATED.value())
                        .message("Orden de compra creada existosamente.")
                        .data(orderCreated)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<PurchaseOrderResponseDTO>>> getAllPurchaseOrders() {

        List<PurchaseOrderResponseDTO> orders = purchaseOrderService.getAllPurchaseOrders();

        return ResponseEntity.ok(
                ApiResponse.<List<PurchaseOrderResponseDTO>>builder()
                        .status(HttpStatus.OK.value())
                        .message("Ordenes de compra obtenidas exitosamente")
                        .data(orders)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PurchaseOrderResponseDTO>> getPurchaseOrderById(@PathVariable Integer id) {

        PurchaseOrderResponseDTO order = purchaseOrderService.getPurchaseOrderById(id);

        return ResponseEntity.ok(
                ApiResponse.<PurchaseOrderResponseDTO>builder()
                        .status(HttpStatus.OK.value())
                        .message("Orden de compra obtenida exitosamente")
                        .data(order)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @GetMapping("/supplier/{supplierId}")
    public ResponseEntity<ApiResponse<List<PurchaseOrderResponseDTO>>> getPurchaseOrdersBySupplier(@PathVariable Integer supplierId) {

        List<PurchaseOrderResponseDTO> orders = purchaseOrderService.getAllPurchaseOrdersBySupplier(supplierId);

        return ResponseEntity.ok(
                ApiResponse.<List<PurchaseOrderResponseDTO>>builder()
                        .status(HttpStatus.OK.value())
                        .message("Ordenes de compra obtenidas por porveedor exitosamente")
                        .data(orders)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<PurchaseOrderResponseDTO>> updatePurchaseOrderStatus(
            @PathVariable Integer id,
            @RequestParam(name = "status") String status) {

        PurchaseOrderResponseDTO updatedOrder = purchaseOrderService.updateOrderStatus(id, status);

        return ResponseEntity.ok(
                ApiResponse.<PurchaseOrderResponseDTO>builder()
                        .status(HttpStatus.OK.value())
                        .message("Estado de la orden actualizado existosamente.")
                        .data(updatedOrder)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

}
