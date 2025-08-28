package com.fotaleza.fortalezaapi.controller;

import com.fotaleza.fortalezaapi.dto.request.SupplierRequestDto;
import com.fotaleza.fortalezaapi.dto.response.MessageResponse;
import com.fotaleza.fortalezaapi.dto.response.SupplierResponseDto;
import com.fotaleza.fortalezaapi.mapper.SupplierMapperDto;
import com.fotaleza.fortalezaapi.model.Supplier;
import com.fotaleza.fortalezaapi.service.impl.SupplierServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/supplier")
public class SupplierController {

    private final SupplierServiceImpl supplierService;

    @Autowired
    public SupplierController(SupplierServiceImpl supplierService) {
        this.supplierService = supplierService;
    }

    @GetMapping("/getAllSuppliers")
    public ResponseEntity<?> getAllSuppliers(@RequestParam("isActivate") boolean isActivate) {

        List<SupplierResponseDto> supplierResponseDtoList = SupplierMapperDto.toModelListResponse(supplierService.getAllSuppliers());

        if (isActivate) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new MessageResponse("Proveedores no encontrados", null));
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(supplierResponseDtoList);
    }

    @GetMapping()
    public ResponseEntity<?> getSupplierById(@RequestParam("supplierId") Integer supplierId) {

        Supplier supplier = supplierService.getSupplierById(supplierId);

        if (Objects.nonNull(supplier)) {

            SupplierResponseDto supplierResponseDto = SupplierMapperDto.toModel(supplier);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(supplierResponseDto);
        } else {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new MessageResponse("Proveedor no encontrado.", null));
        }
    }

    @PostMapping
    public ResponseEntity<?> createSupplier(@Valid @RequestBody SupplierRequestDto supplierRequestDto) {

        if (supplierService.existsBySupplierName(supplierRequestDto.getName())) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponse(
                            String.format("El proveedor %s ya esta registrado!", supplierRequestDto.getName()), supplierRequestDto));
        }

        Supplier newSupplier = SupplierMapperDto.toEntity(supplierRequestDto);
        newSupplier.setCreatedDateTime(new Date());
        newSupplier.setUpdatedDateTime(new Date());
        newSupplier.setIsActivate(true);

        supplierService.createSupplier(newSupplier);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new MessageResponse(
                        String.format("Proveedor %s agregado correctamente.", newSupplier.getName()), newSupplier));
    }

    @PutMapping
    public ResponseEntity<?> updateSupplier(@Valid @RequestBody SupplierRequestDto supplierRequestDto) {

        Supplier supplierToUpdate = supplierService.getSupplierById(supplierRequestDto.getId());

        if (Objects.nonNull(supplierToUpdate)) {
            if (supplierService.existsBySupplierName(supplierRequestDto.getName())) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(new MessageResponse(
                                String.format("No se pudo actualizar el proveedor %s, el proveedor ya se encuentra registrado.", supplierRequestDto.getName()),
                                supplierRequestDto));
            }

            supplierToUpdate = SupplierMapperDto.toEntity(supplierRequestDto);
            supplierToUpdate.setUpdatedDateTime(new Date());

            supplierService.updateSupplier(, supplierToUpdate);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new MessageResponse(
                            String.format(" Proveerdor %s actualizado correctament.", supplierToUpdate.getName()), supplierToUpdate));
        } else {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new MessageResponse(
                            String.format("No existe el proveedor %s.", supplierRequestDto.getName()), null));
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deleteSupplier(@Param("supplierId") Integer supplierId) {

        Supplier supplierToDelete = supplierService.getSupplierById(supplierId);

        if (Objects.nonNull(supplierToDelete)) {
            supplierService.deleteSupplier(supplierToDelete.getId());
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new MessageResponse("El Proveedor eliminado!", supplierToDelete));
        } else {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new MessageResponse("No se encontró el proveedor que desea borrar.", null));
        }
    }
}
