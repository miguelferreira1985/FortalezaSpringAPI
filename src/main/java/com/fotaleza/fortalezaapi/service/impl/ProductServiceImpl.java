package com.fotaleza.fortalezaapi.service.impl;


import com.fotaleza.fortalezaapi.constants.ErrorMessages;
import com.fotaleza.fortalezaapi.dto.request.ProductRequestDTO;
import com.fotaleza.fortalezaapi.dto.response.ProductResponseDTO;
import com.fotaleza.fortalezaapi.enums.EMovementType;
import com.fotaleza.fortalezaapi.exception.ResourceAlreadyExistsException;
import com.fotaleza.fortalezaapi.exception.ResourceNotFoundException;
import com.fotaleza.fortalezaapi.mapper.ProductMapper;
import com.fotaleza.fortalezaapi.model.*;
import com.fotaleza.fortalezaapi.repository.*;
import com.fotaleza.fortalezaapi.service.IInventoryMovementService;
import com.fotaleza.fortalezaapi.service.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements IProductService {

    private final ProductRepository productRepository;
    private final SubcategoryRepository subcategoryRepository;
    private final PresentationRepository presentationRepository;
    private final SupplierProductRepository supplierProductRepository;
    private final SupplierRepository supplierRepository;
    private final ProductMapper productMapper;
    private final IInventoryMovementService inventoryMovementService;

    @Override
    @Transactional
    public ProductResponseDTO createProduct(ProductRequestDTO productRequestDTO) {

        validateNameOrCodeUnique(productRequestDTO.getName(), productRequestDTO.getCode(), null);

        Product product = productMapper.toEntity(productRequestDTO);

        if (productRequestDTO.getSubcategoryId() != null) {
            Subcategory subcategory = subcategoryRepository.findById(productRequestDTO.getSubcategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.PRODUCT_SUBCATEGORY_NOT_FOUND));
            product.setSubcategory(subcategory);
        }

        if (productRequestDTO.getPresentationId() != null) {
            Presentation presentation = presentationRepository.findById(productRequestDTO.getPresentationId())
                    .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.PRODUCT_PRESENTATION_NOT_FOUND));
            product.setPresentation(presentation);
        }

        product.clearSupplierProducts();

        if (productRequestDTO.getSupplierCosts() != null && !productRequestDTO.getSupplierCosts().isEmpty()) {
            for (var sc : productRequestDTO.getSupplierCosts()) {
                Supplier supplier = supplierRepository.findById(sc.getSupplierId())
                        .orElseThrow(() -> new ResourceNotFoundException("Proveedor no encontrado."));

                SupplierProduct supplierProduct = new SupplierProduct();
                supplierProduct.setSupplier(supplier);
                supplierProduct.setCost(sc.getCost());
                supplierProduct.setDiscount(sc.getDiscount());

                product.addSupplierProduct(supplierProduct);
            }
        }

        Product savedProduct = productRepository.save(product);

        return productMapper.toResponseDTO(savedProduct);
    }

    @Override
    @Transactional
    public ProductResponseDTO updateProduct(Integer productId, ProductRequestDTO productRequestDTO) {

        Product productToUpdate =productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(ErrorMessages.PRODUCT_NOT_FOUND, productId)));

        validateNameOrCodeUnique(productRequestDTO.getName(), productRequestDTO.getCode(), productId);

        productMapper.updateEntityFromRequestDTO(productRequestDTO, productToUpdate);

        if (productRequestDTO.getSubcategoryId() != null) {
            Subcategory subcategory = subcategoryRepository.findById(productRequestDTO.getSubcategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.PRODUCT_SUBCATEGORY_NOT_FOUND));
            productToUpdate.setSubcategory(subcategory);
        }

        if (productRequestDTO.getPresentationId() != null) {
            Presentation presentation = presentationRepository.findById(productRequestDTO.getPresentationId())
                    .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.PRODUCT_PRESENTATION_NOT_FOUND));
            productToUpdate.setPresentation(presentation);
        }

        Map<Integer, SupplierProduct> currentBySupplier = productToUpdate.getSupplierProducts().stream()
                .collect(Collectors.toMap(sp -> sp.getSupplier().getId(), sp -> sp));

        Set<Integer> incoming = new HashSet<>();

        if (productRequestDTO.getSupplierCosts() != null && !productRequestDTO.getSupplierCosts().isEmpty()) {
            for (var sc : productRequestDTO.getSupplierCosts()) {
                int supplierId = sc.getSupplierId();
                incoming.add(supplierId);

                SupplierProduct supplierProduct = currentBySupplier.get(supplierId);

                if (supplierProduct == null) {
                    Supplier supplier = supplierRepository.findById(supplierId)
                            .orElseThrow(() -> new ResourceNotFoundException("Proveedor no encontrado."));
                    supplierProduct = new SupplierProduct();
                    supplierProduct.setSupplier(supplier);
                    productToUpdate.addSupplierProduct(supplierProduct);
                }
                supplierProduct.setCost(sc.getCost());
                supplierProduct.setDiscount(sc.getDiscount());
            }
        }

        productToUpdate.getSupplierProducts().removeIf(sp -> !incoming.contains(sp.getSupplier().getId()));

        Product updatedProduct = productRepository.save(productToUpdate);

        return productMapper.toResponseDTO(updatedProduct);
    }

    @Override
    public ProductResponseDTO getProductById(Integer productId) {
        Product product = productRepository.findByIdWithSuppliers(productId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(ErrorMessages.PRODUCT_NOT_FOUND, productId)));
        return productMapper.toResponseDTO(product);
    }

    @Override
    public List<ProductResponseDTO> getAllProducts(Boolean isActivate) {
        List<Product> products = productRepository.findAllWithSuppliersAndStatus(isActivate);
        return productMapper.toResponseDTOList(products);
    }

    @Override
    @Transactional
    public ProductResponseDTO activateProduct(Integer productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(ErrorMessages.PRODUCT_NOT_FOUND, productId)));
        product.setIsActivate(true);

        Product savedProduct = productRepository.save(product);

        return productMapper.toResponseDTO(savedProduct);
    }

    @Override
    @Transactional
    public ProductResponseDTO deactivateProduct(Integer productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(ErrorMessages.PRODUCT_NOT_FOUND, productId)));
        product.setIsActivate(false);

        Product savedProduct = productRepository.save(product);

        return productMapper.toResponseDTO(savedProduct);
    }

    @Override
    public BigDecimal getInventoryValue() {
        return productRepository.calculateTotalInventoryValue();
    }

    @Override
    @Transactional
    public ProductResponseDTO updateProductStock(Integer productId, BigDecimal quantity, String description) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(ErrorMessages.PRODUCT_NOT_FOUND, productId)));

        BigDecimal previousStock = product.getStock();
        BigDecimal newStock = previousStock.add(quantity);

        if (newStock.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalStateException("El stock no puede ser negativo");
        }

        product.setStock(newStock);

        Product productToUpdate = productRepository.save(product);

        inventoryMovementService.recordMovement(product, quantity, EMovementType.AJUSTE, previousStock, newStock, description);

        return productMapper.toResponseDTO(productToUpdate);
    }


    private void validateNameOrCodeUnique(String name, String code, Integer productId) {

        final String errorMessage = String.format(ErrorMessages.PRODUCT_ALREADY_EXISTS, name.toUpperCase(), code.toUpperCase());

        if (productId == null) {
            productRepository.findByNameOrCode(name, code)
                    .ifPresent(p -> {
                            throw new ResourceAlreadyExistsException(errorMessage);
                    });
        } else {
            productRepository.findByNameOrCodeAndIdNot(name, code, productId)
                    .ifPresent(p -> {
                        throw new ResourceAlreadyExistsException(errorMessage);
                    });
        }

    }

    private Set<SupplierProduct> mapSupplierCost(ProductRequestDTO productRequestDTO, Product product) {
        return productRequestDTO.getSupplierCosts()
                .stream()
                .map(costDto -> {
                    Supplier supplier = supplierRepository.findById(costDto.getSupplierId())
                            .orElseThrow(() -> new ResourceNotFoundException(
                                    "Proveedor no encontrado con ID: " + costDto.getSupplierId()));
                    SupplierProduct sp = new SupplierProduct();
                    sp.setSupplier(supplier);
                    sp.setProduct(product);
                    sp.setCost(costDto.getCost());
                    sp.setDiscount(costDto.getDiscount());
                    sp.setId(new SupplierProductId(supplier.getId(), product.getId()));
                    return sp;
                })
                .collect(Collectors.toSet());
    }

}
