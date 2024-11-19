package com.fotaleza.fortalezaapi.controller;

import com.fotaleza.fortalezaapi.model.Color;
import com.fotaleza.fortalezaapi.service.impl.ColorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/color")
public class ColorController {

    @Autowired
    private ColorService colorService;

    @GetMapping("/getAllColors")
    public List<Color> getAllColors() {
        return colorService.getAllColors();
    }

    @GetMapping()
    public ResponseEntity<Color> getColorById(@RequestParam("colorId") Integer colorId) {

        Color color = colorService.getColorById(colorId);

        if (color != null) {
            return new ResponseEntity<Color>(colorService.getColorById(colorId), HttpStatus.OK);
        } else {
            return new ResponseEntity<Color>(colorService.getColorById(colorId), HttpStatus.NOT_FOUND);
        }

    }

    @PostMapping
    public ResponseEntity<Color> createColor(@RequestBody Color color) {

        if(color.getName() != null) {
            return new ResponseEntity<Color>(colorService.saveColor(color), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @PutMapping()
    public ResponseEntity<Color> updateColor(@RequestParam("colorId") Integer colorId, @RequestBody Color color) {

        Color colorUpdated = colorService.updateColor(colorId, color);

        if (colorUpdated != null) {
            return new ResponseEntity<Color>(colorUpdated, HttpStatus.OK);
        } else {
            return new ResponseEntity<Color>(colorUpdated, HttpStatus.BAD_REQUEST);
        }

    }
}
