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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/supplier")
public class SupplierController {

    @Autowired
    private SupplierServiceImpl supplierService;

    @GetMapping("/getAllSuppliers")
    public ResponseEntity<?> getAllSuppliers(@RequestParam("isActivate") boolean isActivate) {

        List<SupplierResponseDto> supplierResponseDtoList = new ArrayList<>();

        if (isActivate) {
            supplierResponseDtoList = SupplierMapperDto.toModelListResponse(supplierService.getAllActivateSuppliers());
        } else {
            supplierResponseDtoList = SupplierMapperDto.toModelListResponse(supplierService.getAllInactivateSuppliers());
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
                    .body(new MessageResponse("Este proveedor ya esta registrado!", supplierRequestDto));
        }

        Supplier newSupplier = SupplierMapperDto.toEntity(supplierRequestDto);
        newSupplier.setCreatedDateTime(new Date());
        newSupplier.setUpdatedDateTime(new Date());
        newSupplier.setIsActivate(true);

        supplierService.saveSupplier(newSupplier);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new MessageResponse("Proveedor " + newSupplier.getName() + " agregado correctamente.", newSupplier));
    }

    @PutMapping
    public ResponseEntity<?> updateSupplier(@Valid @RequestBody SupplierRequestDto supplierRequestDto) {

        Supplier supplierToUpdate = supplierService.getSupplierById(supplierRequestDto.getId());

        if (Objects.nonNull(supplierToUpdate)) {
            if (supplierService.existsBySupplierName(supplierRequestDto.getName())) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(new MessageResponse("No se pudo actualizar el proveedor " + supplierRequestDto.getName() + " , el proveedor ya se encuentra registrado.", supplierRequestDto));
            }

            supplierToUpdate = SupplierMapperDto.toEntity(supplierRequestDto);
            supplierToUpdate.setUpdatedDateTime(new Date());

            supplierService.updateSupplier(supplierToUpdate);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new MessageResponse(" Proveerdor " + supplierToUpdate.getName() + " actualizado correctament.", supplierToUpdate));
        } else {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new MessageResponse("No existe el proveedor " + supplierRequestDto.getName() + ".", null));
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deleteSupplier(@Param("supplierId") Integer supplierId) {

        Supplier supplierToDelete = supplierService.getSupplierById(supplierId);

        if (Objects.nonNull(supplierToDelete)) {
            supplierService.deleteSupplier(supplierToDelete.getId());
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new MessageResponse("Empleado eliminado con exitoso!", supplierToDelete));
        } else {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new MessageResponse("No se encontro el empleado que desea borrar.", null));
        }
    }
}
