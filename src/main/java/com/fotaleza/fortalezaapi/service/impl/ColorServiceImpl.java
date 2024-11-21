package com.fotaleza.fortalezaapi.service.impl;

import com.fotaleza.fortalezaapi.model.Color;
import com.fotaleza.fortalezaapi.repository.ColorRepository;
import com.fotaleza.fortalezaapi.service.IColorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ColorServiceImpl implements IColorService {

    @Autowired
    private ColorRepository colorRepository;

    @Override
    public Color saveColor(Color color) {
        return colorRepository.save(color);
    }

    @Override
    public List<Color> getAllColors() {
        return colorRepository.findAll();
    }

    @Override
    public Color getColorById(Integer colorId) {
        return colorRepository.findById(colorId).orElse(null);
    }

    @Override
    public Color getColorByName(String colorName) {

        Color color = colorRepository.findByName(colorName)
                .orElseThrow(() -> new RuntimeException("Color no encontrado con el nombre: " + colorName));

        return color;
    }

    @Override
    public Boolean existsByColorName(String colorName) { return colorRepository.existsByName(colorName); }

    @Override
    public Color updateColor(Color color) { return colorRepository.save(color); }

    @Override
    public void deleteColor(Integer colorId) {
        colorRepository.deleteById(colorId);
    }
}
