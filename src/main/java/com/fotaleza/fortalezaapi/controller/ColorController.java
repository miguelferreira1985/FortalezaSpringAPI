package com.fotaleza.fortalezaapi.controller;

import com.fotaleza.fortalezaapi.model.Color;
import com.fotaleza.fortalezaapi.dto.response.MessageResponse;
import com.fotaleza.fortalezaapi.service.impl.ColorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/color")
public class ColorController {

    @Autowired
    private ColorServiceImpl colorServiceImpl;

    @GetMapping("/getAllColors")
    public List<Color> getAllColors() {
        return colorServiceImpl.getAllColors();
    }

    @GetMapping()
    public ResponseEntity<?> getColorById(@RequestParam("colorId") Integer colorId) {

        Color color = colorServiceImpl.getColorById(colorId);

        if (color != null) {
            return ResponseEntity.ok().body(color);
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @PostMapping
    public ResponseEntity<?> createColor(@RequestBody Color color) {

        if (color.getName() != null) {
            if (colorServiceImpl.existsByColorName(color.getName())) {
                return ResponseEntity.badRequest()
                        .body(new MessageResponse("Este Color ya esta registrado", color));
            }
            colorServiceImpl.saveColor(color);
            return ResponseEntity.ok(new MessageResponse("Color " + color.getName() + " agregado correctamente", color));
        } else {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse("No se pudo guardar el color " + color.getName() + ".", color));
        }
    }

    @PutMapping()
    public ResponseEntity<?> updateColor(@RequestBody Color color) {

        Color colorToUpdated = colorServiceImpl.getColorById(color.getId());

        if (colorToUpdated != null) {
            if (colorServiceImpl.existsByColorName(color.getName())) {
                return ResponseEntity.badRequest()
                        .body(new MessageResponse("No se pudo actualizar el color " + color.getName() + " el color ya existe.", color));
            }
            colorServiceImpl.updateColor(color);
            return ResponseEntity.ok(new MessageResponse("Color " + color.getName() + " actualicado correctamente", color));
        } else {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse("No existe el color " + color.getName() + ".", color));
        }
    }
}
