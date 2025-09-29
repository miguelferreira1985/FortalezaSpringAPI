package com.fotaleza.fortalezaapi.controller;

import com.fotaleza.fortalezaapi.dto.response.ApiResponse;
import com.fotaleza.fortalezaapi.dto.response.ProductResponseDTO;
import com.fotaleza.fortalezaapi.dto.request.SupplierRequestDTO;
import com.fotaleza.fortalezaapi.dto.response.SupplierResponseDTO;
import com.fotaleza.fortalezaapi.service.ISupplierService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/suppliers")
@RequiredArgsConstructor
public class SupplierController {

    private final ISupplierService supplierService;

    @PostMapping
    public ResponseEntity<ApiResponse<SupplierResponseDTO>> createSupplier(@Valid @RequestBody SupplierRequestDTO supplierRequestDTO) {

        SupplierResponseDTO createdSupplier = supplierService.createSupplier(supplierRequestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.<SupplierResponseDTO>builder()
                        .status(HttpStatus.CREATED.value())
                        .message(String.format("El proveedor %s ha sido agregado.", supplierRequestDTO.getName().toUpperCase()))
                        .data(createdSupplier)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<SupplierResponseDTO>> updateSupplier(@PathVariable Integer id, @Valid @RequestBody SupplierRequestDTO supplierRequestDTO) {

        SupplierResponseDTO updatedSupplier = supplierService.updateSupplier(id, supplierRequestDTO);

        return ResponseEntity.ok(
                ApiResponse.<SupplierResponseDTO>builder()
                        .status(HttpStatus.OK.value())
                        .message(String.format("El proveedor %s ha sido actualizado.", supplierRequestDTO.getName().toUpperCase()))
                        .data(updatedSupplier)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<ApiResponse<SupplierResponseDTO>> deactivateSupplier(@PathVariable Integer id) {

        SupplierResponseDTO supplierDeactivated = supplierService.deactivateSupplierAndReassignProducts(id);

        return ResponseEntity.ok(
                ApiResponse.<SupplierResponseDTO>builder()
                        .status(HttpStatus.OK.value())
                        .message(String.format("El proveedor %s ha sido desactivado.", supplierDeactivated.getName().toUpperCase()))
                        .data(supplierDeactivated)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @PatchMapping("/{id}/activate")
    public ResponseEntity<ApiResponse<SupplierResponseDTO>> activateSupplier(@PathVariable Integer id) {

        SupplierResponseDTO supplierDeactivated = supplierService.activateSupplier(id);

        return ResponseEntity.ok(
                ApiResponse.<SupplierResponseDTO>builder()
                        .status(HttpStatus.OK.value())
                        .message(String.format("El proveedor %s ha sido activado.", supplierDeactivated.getName().toUpperCase()))
                        .data(supplierDeactivated)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<SupplierResponseDTO>> getSupplierById(@PathVariable Integer id) {

        SupplierResponseDTO supplier = supplierService.getSupplierById(id);

        return ResponseEntity.ok(
                ApiResponse.<SupplierResponseDTO>builder()
                        .status(HttpStatus.OK.value())
                        .message("Proveedor obtenido existosamente.")
                        .data(supplier)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @GetMapping()
    public ResponseEntity<ApiResponse<List<SupplierResponseDTO>>> getAllSuppliers(@RequestParam(name = "isActivate", required = false) Boolean isActivate) {

        List<SupplierResponseDTO> suppliers = supplierService.getAllSuppliers(isActivate);

        return ResponseEntity.ok(
                ApiResponse.<List<SupplierResponseDTO>>builder()
                        .status(HttpStatus.OK.value())
                        .message("Proveedores obtenidos existosamente.")
                        .data(suppliers)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @GetMapping("/{id}/products")
    public ResponseEntity<ApiResponse<List<ProductResponseDTO>>> getProductsBySupplier(@PathVariable("id") Integer id) {

        List<ProductResponseDTO> products = supplierService.getProductsOfSupplier(id);

        return ResponseEntity.ok(
                ApiResponse.<List<ProductResponseDTO>>builder()
                        .status(HttpStatus.OK.value())
                        .message("Productos del proveedor obtenidos existosamente.")
                        .data(products)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }



}
