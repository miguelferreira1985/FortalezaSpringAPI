package com.fotaleza.fortalezaapi.service.impl;


import com.fotaleza.fortalezaapi.dto.request.ProductRequestDTO;
import com.fotaleza.fortalezaapi.dto.response.ProductResponseDTO;
import com.fotaleza.fortalezaapi.exception.ResourceAlreadyExistsException;
import com.fotaleza.fortalezaapi.exception.ResourceNotFoundException;
import com.fotaleza.fortalezaapi.mapper.ProductMapper;
import com.fotaleza.fortalezaapi.model.Presentation;
import com.fotaleza.fortalezaapi.model.Product;
import com.fotaleza.fortalezaapi.model.Subcategory;
import com.fotaleza.fortalezaapi.model.Supplier;
import com.fotaleza.fortalezaapi.repository.PresentationRepository;
import com.fotaleza.fortalezaapi.repository.ProductRepository;
import com.fotaleza.fortalezaapi.repository.SubcategoryRepository;
import com.fotaleza.fortalezaapi.repository.SupplierRepository;
import com.fotaleza.fortalezaapi.service.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements IProductService {

    private final ProductRepository productRepository;
    private final SubcategoryRepository subcategoryRepository;
    private final PresentationRepository presentationRepository;
    private final SupplierRepository supplierRepository;
    private final ProductMapper productMapper;

    @Override
    @Transactional
    public ProductResponseDTO createProduct(ProductRequestDTO productRequestDTO) {

        validateNameOrCodeUnique(productRequestDTO.getName(), productRequestDTO.getCode(), null);

        Product product = productMapper.toEntity(productRequestDTO);

        Subcategory subcategory = subcategoryRepository.findById(productRequestDTO.getSubcategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Subcategoria no encontrada."));
        product.setSubcategory(subcategory);

        Presentation presentation = presentationRepository.findById(productRequestDTO.getPresentationId())
                .orElseThrow(() -> new ResourceNotFoundException("Presentación no encontrada."));
        product.setPresentation(presentation);

        Set<Supplier> suppliers = new HashSet<>(supplierRepository.findAllById(productRequestDTO.getSupplierIds()));
        product.setSuppliers(suppliers);

        Product savedProduct = productRepository.save(product);
        return productMapper.toResponseDTO(savedProduct);
    }

    @Override
    @Transactional
    public ProductResponseDTO updateProduct(Integer productId, ProductRequestDTO productRequestDTO) {
        Product productToUpdate =productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("El producto no existe"));

        validateNameOrCodeUnique(productRequestDTO.getName(), productRequestDTO.getCode(), productId);

        productMapper.updateEntityFromRequestDTO(productRequestDTO, productToUpdate);

        Subcategory subcategory = subcategoryRepository.findById(productRequestDTO.getSubcategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Subcategoria no encontrada."));
        productToUpdate.setSubcategory(subcategory);

        Presentation presentation = presentationRepository.findById(productRequestDTO.getPresentationId())
                .orElseThrow(() -> new ResourceNotFoundException("Presentación no encontrada."));
        productToUpdate.setPresentation(presentation);

        Set<Supplier> suppliers = new HashSet<>(supplierRepository.findAllById(productRequestDTO.getSupplierIds()));
        productToUpdate.setSuppliers(suppliers);

        Product updatedProduct = productRepository.save(productToUpdate);
        return productMapper.toResponseDTO(updatedProduct);
    }

    @Override
    @Transactional
    public void deleteProduct(Integer productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("El producto no existe"));

        boolean isStockZero = product.getStock().compareTo(BigDecimal.ZERO) == 0;
        boolean isNotActive = !product.getIsActivate();

        if (isStockZero && isNotActive) {
            productRepository.deleteById(productId);
        } else {
            String message = "";
            if (!isStockZero) {
                message = "El stock tiene que estar en 0 para poder eliminar el producto.";
            }

            if (!isNotActive) {
                message = "El producto debe esta desactivado para poder eliminarlo.";
            }

            throw new IllegalStateException(message);
        }
    }

    @Override
    public ProductResponseDTO getProductById(Integer productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("El producto no existe."));
        return productMapper.toResponseDTO(product);
    }

    @Override
    public List<ProductResponseDTO> getAllProducts(Boolean isActivate) {
        List<Product> products = Optional.ofNullable(isActivate)
                .map(productRepository::findByIsActivate)
                .orElseGet(productRepository::findAll);
        return productMapper.toResponseDTOList(products);
    }

    @Override
    @Transactional
    public ProductResponseDTO activateProduct(Integer productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("El producto no existe."));
        product.setIsActivate(true);

        Product savedProduct = productRepository.save(product);

        return productMapper.toResponseDTO(savedProduct);
    }

    @Override
    @Transactional
    public ProductResponseDTO deactivateProduct(Integer productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("El producto no existe."));
        product.setIsActivate(false);

        Product savedProduct = productRepository.save(product);

        return productMapper.toResponseDTO(savedProduct);
    }

    @Override
    public BigDecimal getInventoryValue() {
        return productRepository.calculateTotalInventoryValue();
    }



    private void validateNameOrCodeUnique(String name, String code, Integer productId) {

        if (productId == null) {
            productRepository.findByNameOrCode(name, code)
                    .ifPresent(p -> {
                            throw new ResourceAlreadyExistsException("El producto con el nombre o codigo ya existe.;");
                    });
        } else {
            productRepository.findByNameOrCodeAndIdNot(name, code, productId)
                    .ifPresent(p -> {
                        throw new ResourceAlreadyExistsException("El producto con el nombre o codigo ya existe.;");
                    });
        }

    }

}
