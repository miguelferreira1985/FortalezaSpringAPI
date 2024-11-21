package com.fotaleza.fortalezaapi.controller;

import com.fotaleza.fortalezaapi.model.Color;
import com.fotaleza.fortalezaapi.service.impl.ColorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<Color> getColorById(@RequestParam("colorId") Integer colorId) {

        Color color = colorServiceImpl.getColorById(colorId);

        if (color != null) {
            return new ResponseEntity<Color>(colorServiceImpl.getColorById(colorId), HttpStatus.OK);
        } else {
            return new ResponseEntity<Color>(colorServiceImpl.getColorById(colorId), HttpStatus.NOT_FOUND);
        }

    }

    @PostMapping
    public ResponseEntity<Color> createColor(@RequestBody Color color) {

        if(color.getName() != null) {
            return new ResponseEntity<Color>(colorServiceImpl.saveColor(color), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @PutMapping()
    public ResponseEntity<?> updateColor(@RequestBody Color color) {

        Color colorUpdated = colorServiceImpl.updateColor(color);

        if (colorUpdated != null) {
            return new ResponseEntity<Color>(colorUpdated, HttpStatus.OK);
        } else {
            return new ResponseEntity<Color>(colorUpdated, HttpStatus.BAD_REQUEST);
        }

    }
}
