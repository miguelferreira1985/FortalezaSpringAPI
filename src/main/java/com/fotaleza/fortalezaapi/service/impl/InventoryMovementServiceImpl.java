package com.fotaleza.fortalezaapi.service.impl;

import com.fotaleza.fortalezaapi.dto.InventoryMovementDTO;
import com.fotaleza.fortalezaapi.enums.EMovementType;
import com.fotaleza.fortalezaapi.exception.ResourceNotFoundException;
import com.fotaleza.fortalezaapi.mapper.InventoryMovementMapper;
import com.fotaleza.fortalezaapi.model.InventoryMovement;
import com.fotaleza.fortalezaapi.model.Product;
import com.fotaleza.fortalezaapi.repository.InventoryMovementRepository;
import com.fotaleza.fortalezaapi.repository.ProductRepository;
import com.fotaleza.fortalezaapi.service.IInventoryMovementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryMovementServiceImpl implements IInventoryMovementService {

    private final InventoryMovementRepository inventoryMovementRepository;
    private final ProductRepository productRepository;
    private final InventoryMovementMapper inventoryMovementMapper;

    @Override
    public InventoryMovement recordMovement(Product product, BigDecimal quantity, EMovementType movementType) {

        BigDecimal previousStock = product.getStock();
        BigDecimal newStock = previousStock.add(quantity);

        InventoryMovement movement = new InventoryMovement();
        movement.setProduct(product);
        movement.setQuantity(quantity);
        movement.setPreviousStock(previousStock);
        movement.setNewStock(newStock);
        movement.setMovementType(movementType);

        return inventoryMovementRepository.save(movement);
    }

    @Override
    public List<InventoryMovementDTO> getMovementsByProduct(Integer productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado."));

        List<InventoryMovement> movements = inventoryMovementRepository.findByProductOrderByMovementDateDesc(product);

        return inventoryMovementMapper.toDTOList(movements);
    }
}
