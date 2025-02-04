package com.fotaleza.fortalezaapi.controller;

import com.fotaleza.fortalezaapi.dto.request.ColorRequestDto;
import com.fotaleza.fortalezaapi.dto.response.ColorResponseDto;
import com.fotaleza.fortalezaapi.model.Color;
import com.fotaleza.fortalezaapi.dto.response.MessageResponse;
import com.fotaleza.fortalezaapi.service.impl.ColorServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/color")
public class ColorController {

    private final ColorServiceImpl colorService;

    @Autowired
    public ColorController(ColorServiceImpl colorService) {
        this.colorService = colorService;
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'CASHIER')")
    @GetMapping("/getAllColors")
    public ResponseEntity<?> getAllColors(@RequestParam("isActivate") boolean isActivate) {

        List<Color> colors;

        if (isActivate) {
            colors = colorService.getAllActivateColors();
        } else {
            colors = colorService.getAllInactivateColors();
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(colors);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'CASHIER')")
    @GetMapping()
    public ResponseEntity<?> getColorById(@RequestParam("colorId") Integer colorId) {

        Color color = colorService.getColorById(colorId);

        if (Objects.nonNull(color)) {

            ColorResponseDto colorResponseDto = new ColorResponseDto();
            colorResponseDto.setId(color.getId());
            colorResponseDto.setName(color.getName());
            colorResponseDto.setCreatedDateTime(color.getCreatedDateTime());
            colorResponseDto.setUpdatedDateTime(color.getUpdatedDateTime());

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(colorResponseDto);
        } else {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new MessageResponse("Color no encontrado.", null));
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'CASHIER')")
    @PostMapping
    public ResponseEntity<?> createColor(@Valid @RequestBody ColorRequestDto colorRequestDto) {

        if (colorService.existsByColorName(colorRequestDto.getName())) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponse(
                            String.format("El color %s ya esta registrado", colorRequestDto.getName()), colorRequestDto));
        }

        Color newColor = new Color();
        newColor.setName(colorRequestDto.getName());
        newColor.setCreatedDateTime(new Date());
        newColor.setUpdatedDateTime(new Date());

        colorService.saveColor(newColor);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new MessageResponse(
                        String.format("Color %s agregado correctamente.", newColor.getName()), newColor));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'CASHIER')")
    @PutMapping()
    public ResponseEntity<?> updateColor(@Valid @RequestBody ColorRequestDto colorRequestDto) {

        Color colorToUpdated = colorService.getColorById(colorRequestDto.getId());

        if (Objects.nonNull(colorToUpdated)) {
            if (colorService.existsByColorName(colorRequestDto.getName())) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(new MessageResponse(
                                String.format("No se pudo actualizar el color %s, el color ya existe.", colorRequestDto.getName()), colorRequestDto));
            }

            colorToUpdated.setId(colorToUpdated.getId());
            colorToUpdated.setName(colorRequestDto.getName());
            colorToUpdated.setUpdatedDateTime(new Date());

            colorService.updateColor(colorToUpdated);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new MessageResponse(
                            String.format("Color %s actualicado correctamente.", colorToUpdated.getName()), colorToUpdated));
        } else {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new MessageResponse(
                            String.format("No existe el color %s.", colorRequestDto.getName()), null));
        }
    }
}
