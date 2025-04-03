package com.fotaleza.fortalezaapi.controller;

import com.fotaleza.fortalezaapi.dto.request.SubcategoryRequestDto;
import com.fotaleza.fortalezaapi.dto.response.MessageResponse;
import com.fotaleza.fortalezaapi.dto.response.SubCategoryResponseDto;
import com.fotaleza.fortalezaapi.mapper.SubcategoryMapperDto;
import com.fotaleza.fortalezaapi.model.Subcategory;
import com.fotaleza.fortalezaapi.service.impl.SubcategoryServiceImpl;
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
@RequestMapping("/api/v1/subcategory")
public class SubcategoryController {

    private final SubcategoryServiceImpl subcategoryService;

    @Autowired
    public SubcategoryController(SubcategoryServiceImpl subcategoryService) {
        this.subcategoryService = subcategoryService;
    }

    @GetMapping("/getAllSubcategories")
    public ResponseEntity<?> getAllCategories(@RequestParam("isActivate") Boolean isActivate) {

        List<SubCategoryResponseDto> subCategoryResponseDtoList =
                SubcategoryMapperDto.toModelListResponse(subcategoryService.getAllSubcategories(isActivate));

        if (subCategoryResponseDtoList.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new MessageResponse("No se encontraron subcategorias", null));
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(subCategoryResponseDtoList);
    }

    @GetMapping()
    public ResponseEntity<?> getSubcategoryById(@RequestParam("subcategoryId") Integer subcategoryId) {

        Subcategory subcategory = subcategoryService.getSubcategoryById(subcategoryId);

        if (Objects.nonNull(subcategory)) {
            SubCategoryResponseDto subCategoryResponseDto = SubcategoryMapperDto.toModel(subcategory);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(subCategoryResponseDto);
        } else {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new MessageResponse("Subcategoria no encontrada", null));
        }
    }

    @PostMapping
    public ResponseEntity<?> createSubcategory(@Valid @RequestBody SubcategoryRequestDto subcategoryRequestDto) {

        if (subcategoryService.existsBySubcategoryName(subcategoryRequestDto.getName())) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponse(
                            String.format("La Subcategoria %s ya esta registrada!", subcategoryRequestDto.getName()), subcategoryRequestDto));
        }

        Subcategory newSubcategory = SubcategoryMapperDto.toEntity(subcategoryRequestDto);
        newSubcategory.setCreatedDateTime(new Date());
        newSubcategory.setUpdatedDateTime(new Date());
        newSubcategory.setIsActivate(true);

        subcategoryService.saveSubcategory(newSubcategory);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new MessageResponse(
                        String.format("Subcategoria %s agregada con exito.", newSubcategory.getName()), newSubcategory));
    }

    @PutMapping
    public ResponseEntity<?> updateSubcategory(@Valid @RequestBody SubcategoryRequestDto subcategoryRequestDto) {

        Subcategory subcategoryToUpdate = subcategoryService.getSubcategoryById(subcategoryRequestDto.getId());

        if (Objects.nonNull(subcategoryToUpdate)) {
            if (subcategoryService.existsBySubcategoryName(subcategoryRequestDto.getName())) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(new MessageResponse(
                                String.format("No se pudo actualizar la subcategoria %s, la subcategoria ya se encuentra registrada.", subcategoryRequestDto.getName()),
                                subcategoryRequestDto.getName()));
            }

            subcategoryToUpdate = SubcategoryMapperDto.toEntity(subcategoryRequestDto);
            subcategoryToUpdate.setUpdatedDateTime(new Date());

            subcategoryService.updateSubcategory(subcategoryToUpdate);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new MessageResponse(
                            String.format("Subcategoria %s actualizada correctamente.", subcategoryToUpdate.getName()),
                            subcategoryToUpdate));
        } else {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new MessageResponse(
                            String.format("No existe la subcategoria %s.", subcategoryRequestDto.getName()), null));
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deleteSubcategory(@Param("subcategoryId") Integer subcategoryId) {

        Subcategory subcategoryToDelete = subcategoryService.getSubcategoryById(subcategoryId);

        if (Objects.nonNull(subcategoryToDelete)) {
            subcategoryService.deleteSubcategory(subcategoryId);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new MessageResponse("Subcategoria elimada con exito", subcategoryToDelete));
        } else {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new MessageResponse("No se encontro la subcategoria que desea elimnar", null));
        }
    }
}
