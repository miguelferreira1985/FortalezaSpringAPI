package com.fotaleza.fortalezaapi.controller;

import com.fotaleza.fortalezaapi.dto.InventoryMovementDTO;
import com.fotaleza.fortalezaapi.dto.response.ApiResponse;
import com.fotaleza.fortalezaapi.service.IInventoryMovementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/inventory-movements")
@RequiredArgsConstructor
public class InventoryMovementController {

    private final IInventoryMovementService inventoryMovementService;

    @GetMapping("product/{id}")
    public ResponseEntity<ApiResponse<List<InventoryMovementDTO>>> getInventoryMovementsByProductId(
            @PathVariable Integer id) {
        List<InventoryMovementDTO> movements = inventoryMovementService.getMovementsByProduct(id);

        return ResponseEntity.ok(
                ApiResponse.<List<InventoryMovementDTO>>builder()
                        .status(HttpStatus.OK.value())
                        .message("Movimientos obtenidos exitosamente")
                        .data(movements)
                        .build()
        );
    }
}
