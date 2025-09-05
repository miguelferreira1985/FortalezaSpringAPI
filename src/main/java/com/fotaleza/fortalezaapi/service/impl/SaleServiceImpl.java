package com.fotaleza.fortalezaapi.service.impl;

import com.fotaleza.fortalezaapi.dto.SaleRequestDTO;
import com.fotaleza.fortalezaapi.dto.SaleResponseDTO;
import com.fotaleza.fortalezaapi.exception.ResourceNotFoundException;
import com.fotaleza.fortalezaapi.mapper.SaleMapper;
import com.fotaleza.fortalezaapi.model.CashStart;
import com.fotaleza.fortalezaapi.model.Product;
import com.fotaleza.fortalezaapi.model.Sale;
import com.fotaleza.fortalezaapi.model.SaleItem;
import com.fotaleza.fortalezaapi.repository.CashStartRepository;
import com.fotaleza.fortalezaapi.repository.ProductRepository;
import com.fotaleza.fortalezaapi.repository.SaleRepository;
import com.fotaleza.fortalezaapi.service.ISaleService;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SaleServiceImpl implements ISaleService {

    private final SaleRepository saleRepository;
    private final ProductRepository productRepository;
    private final CashStartRepository cashStartRepository;
    private final SaleMapper saleMapper;

    @Override
    @Transactional
    public SaleResponseDTO createSale(SaleRequestDTO saleRequestDTO) {

        CashStart activeCashStart = null;
        if (saleRequestDTO.getPaymentType().isCashPayment()) {
            activeCashStart = cashStartRepository.findByEndDateTimeIsNull()
                    .orElseThrow(() -> new ResourceNotFoundException("No hay una caja activa."));
        }

        Sale newSale = new Sale();
        newSale.setPaymentType(saleRequestDTO.getPaymentType());
        newSale.setCashStart(activeCashStart);

        Set<SaleItem> saleItems = saleRequestDTO.getSaleItems().stream()
                .map(saleItemRequestDto -> {

                    // Paso 1: Mapear el DTO a la entidad de SaleItem
                    SaleItem saleItem = saleMapper.toSaleItemEntity(saleItemRequestDto);

                    // Paso 2 y 3: Obtener el producto y validar si existe
                    Product product = productRepository.findById(saleItemRequestDto.getProductId())
                            .orElseThrow(() -> new ResourceNotFoundException("El producto no existe."));

                    // Paso 4: Validar el stock
                    if (saleItemRequestDto.getQuantity().intValue() > product.getStock().intValue()) {
                        throw new ValidationException("No hay suficiente stock para el producto.");
                    }

                    // Paso 5: Reducir el stock del producto
                    product.setStock(product.getStock().subtract(saleItemRequestDto.getQuantity()));
                    productRepository.save(product); // Guarda el cambio de stock

                    // Paso 6: Calcular el subtotal y configurar el item
                    BigDecimal subtotal = product.getPrice().multiply(saleItemRequestDto.getQuantity());
                    saleItem.setUnitPrice(product.getPrice());
                    saleItem.setSubtotal(subtotal);
                    saleItem.setProduct(product);
                    saleItem.setSale(newSale);

                    return saleItem;
                })
                .collect(Collectors.toSet());

        newSale.setSaleItems(saleItems);

        BigDecimal totalAmount = saleItems.stream()
                .map(SaleItem::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        newSale.setTotalAmount(totalAmount);

        Sale savedSale = saleRepository.save(newSale);

        return saleMapper.toResponseDTO(savedSale);
    }
}
