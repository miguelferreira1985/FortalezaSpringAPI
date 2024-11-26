package com.fotaleza.fortalezaapi.controller;

import com.fotaleza.fortalezaapi.model.Color;
import com.fotaleza.fortalezaapi.payload.response.MessageResponse;
import com.fotaleza.fortalezaapi.service.impl.ColorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// for Angular Client (withCredentials)
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials = "true")
//@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/color")
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
            //return new ResponseEntity<Color>(colorServiceImpl.getColorById(colorId), HttpStatus.OK);
        } else {
            return (ResponseEntity<?>) ResponseEntity.notFound().build().getBody();
            //return new ResponseEntity<Color>(colorServiceImpl.getColorById(colorId), HttpStatus.NOT_FOUND);
        }

    }

    @PostMapping
    public ResponseEntity<?> createColor(@RequestBody Color color) {

        if (color.getName() != null) {
            if (colorServiceImpl.existsByColorName(color.getName())) {
                return ResponseEntity.badRequest()
                        .body(new MessageResponse("Error: Este Color ya esta registrado", color));
            }
            colorServiceImpl.saveColor(color);
            return ResponseEntity.ok(new MessageResponse("Color " + color.getName() + " agregado correctamente", color));
            //return new ResponseEntity<Color>(colorServiceImpl.saveColor(color), HttpStatus.CREATED);
        } else {
            return ResponseEntity.badRequest().build();
            //return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @PutMapping()
    public ResponseEntity<?> updateColor(@RequestBody Color color) {

        Color colorToUpdated = colorServiceImpl.getColorById(color.getId());

        if (colorToUpdated != null) {
            colorServiceImpl.updateColor(color);
            return ResponseEntity.ok(color);
            //return new ResponseEntity<Color>(colorUpdated, HttpStatus.OK);
        } else {
            return ResponseEntity.badRequest().build();
            //return new ResponseEntity<Color>(colorUpdated, HttpStatus.BAD_REQUEST);
        }

    }
}
