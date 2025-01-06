package com.fotaleza.fortalezaapi.controller;

import com.fotaleza.fortalezaapi.dto.request.SupplierRequestDto;
import com.fotaleza.fortalezaapi.dto.response.MessageResponse;
import com.fotaleza.fortalezaapi.dto.response.SupplierResponseDto;
import com.fotaleza.fortalezaapi.model.Supplier;
import com.fotaleza.fortalezaapi.service.impl.SupplierServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/supplier")
public class SupplierController {

    @Autowired
    private SupplierServiceImpl supplierService;

    @GetMapping("/getAllSuppliers")
    public List<Supplier> getAllSuppliers() {
        return supplierService.getAllSuppliers();
    }

    @GetMapping()
    public ResponseEntity<?> getSupplierById(@RequestParam("supplierId") Integer supplierId) {

        Supplier supplier = supplierService.getSupplierById(supplierId);

        if (Objects.nonNull(supplier)) {

            SupplierResponseDto supplierResponseDto = new SupplierResponseDto();
            supplierResponseDto.setId(supplier.getId());
            supplierResponseDto.setName(supplier.getName());
            supplierResponseDto.setContact(supplier.getContact());
            supplierResponseDto.setEmail(supplier.getEmail());
            supplierResponseDto.setAddress(supplier.getAddress());
            supplierResponseDto.setPhone(supplier.getPhone());

            return ResponseEntity.ok(supplierResponseDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<?> createSupplier(@Valid @RequestBody SupplierRequestDto supplierRequestDto) {

        if (supplierService.existsBySupplierName(supplierRequestDto.getName())) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse("Este proveedor ya esta registrado!", supplierRequestDto));
        }

        Supplier newSupplier = new Supplier();
        newSupplier.setName(supplierRequestDto.getName());
        newSupplier.setContact(supplierRequestDto.getContact());
        newSupplier.setAddress(supplierRequestDto.getAddress());
        newSupplier.setEmail(supplierRequestDto.getEmail());
        newSupplier.setPhone(supplierRequestDto.getPhone());

        supplierService.saveSupplier(newSupplier);

        return ResponseEntity.ok(new MessageResponse("Proveedor " + newSupplier.getName() + " agregado correctamente.", newSupplier));
    }

    @PutMapping
    public ResponseEntity<?> updateSupplier(@Valid @RequestBody SupplierRequestDto supplierRequestDto) {

        Supplier supplierToUpdate = supplierService.getSupplierById(supplierRequestDto.getId());

        if (Objects.nonNull(supplierToUpdate)) {
            if (supplierService.existsBySupplierName(supplierRequestDto.getName())) {
                return ResponseEntity.badRequest()
                        .body(new MessageResponse("No se pudo actualizar el proveedor " + supplierRequestDto.getName() + " , el proveedor ya se encuentra registrado.", supplierRequestDto));
            }

            supplierToUpdate.setName(supplierRequestDto.getName());
            supplierToUpdate.setContact(supplierRequestDto.getContact());
            supplierToUpdate.setAddress(supplierRequestDto.getAddress());
            supplierToUpdate.setEmail(supplierRequestDto.getEmail());
            supplierToUpdate.setPhone(supplierRequestDto.getPhone());

            supplierService.updateSupplier(supplierToUpdate);

            return ResponseEntity.ok(new MessageResponse(" Proveerdor " + supplierToUpdate.getName() + " actualizado correctament.", supplierToUpdate));
        } else {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse("No existe el proveedor " + supplierRequestDto.getName() + ".", supplierRequestDto));
        }
    }
}
