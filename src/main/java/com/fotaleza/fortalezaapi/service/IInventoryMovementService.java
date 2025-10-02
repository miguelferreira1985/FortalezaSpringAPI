package com.fotaleza.fortalezaapi.service;

import com.fotaleza.fortalezaapi.dto.InventoryMovementDTO;
import com.fotaleza.fortalezaapi.enums.EMovementType;
import com.fotaleza.fortalezaapi.model.InventoryMovement;
import com.fotaleza.fortalezaapi.model.Product;

import java.math.BigDecimal;
import java.util.List;

public interface IInventoryMovementService {

    InventoryMovement recordMovement(Product product, BigDecimal quantity, EMovementType movementType, BigDecimal previousStock, BigDecimal newStock, String description);
    List<InventoryMovementDTO> getMovementsByProduct(Integer productId);

}
