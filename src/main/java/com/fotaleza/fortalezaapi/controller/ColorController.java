package com.fotaleza.fortalezaapi.controller;

import com.fotaleza.fortalezaapi.dto.request.ColorRequestDto;
import com.fotaleza.fortalezaapi.dto.response.ColorResponseDto;
import com.fotaleza.fortalezaapi.model.Color;
import com.fotaleza.fortalezaapi.dto.response.MessageResponse;
import com.fotaleza.fortalezaapi.service.impl.ColorServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/color")
public class ColorController {

    @Autowired
    private ColorServiceImpl colorService;

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'CASHIER')")
    @GetMapping("/getAllColors")
    public List<Color> getAllColors() {
        return colorService.getAllColors();
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'CASHIER')")
    @GetMapping()
    public ResponseEntity<?> getColorById(@RequestParam("colorId") Integer colorId) {

        Color color = colorService.getColorById(colorId);

        if (Objects.nonNull(color)) {

            ColorResponseDto colorResponseDto = new ColorResponseDto();
            colorResponseDto.setId(color.getId());
            colorResponseDto.setName(color.getName());

            return ResponseEntity.ok().body(colorResponseDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'CASHIER')")
    @PostMapping
    public ResponseEntity<?> createColor(@Valid @RequestBody ColorRequestDto colorRequestDto) {

        if (colorService.existsByColorName(colorRequestDto.getName())) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse("Este Color ya esta registrado", colorRequestDto));
        }

        Color newColor = new Color();
        newColor.setName(colorRequestDto.getName());

        colorService.saveColor(newColor);

        return ResponseEntity.ok(new MessageResponse("Color " + newColor.getName() + " agregado correctamente.", newColor));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'CASHIER')")
    @PutMapping()
    public ResponseEntity<?> updateColor(@Valid @RequestBody ColorRequestDto colorRequestDto) {

        Color colorToUpdated = colorService.getColorById(colorRequestDto.getId());

        if (Objects.nonNull(colorToUpdated)) {
            if (colorService.existsByColorName(colorRequestDto.getName())) {
                return ResponseEntity.badRequest()
                        .body(new MessageResponse("No se pudo actualizar el color " + colorRequestDto.getName() + " el color ya existe.", colorRequestDto));
            }

            colorToUpdated.setId(colorToUpdated.getId());
            colorToUpdated.setName(colorRequestDto.getName());

            colorService.updateColor(colorToUpdated);

            return ResponseEntity.ok(new MessageResponse("Color " + colorToUpdated.getName() + " actualicado correctamente", colorToUpdated));
        } else {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse("No existe el color " + colorRequestDto.getName() + ".", colorRequestDto));
        }
    }
}
