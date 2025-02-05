package com.fotaleza.fortalezaapi.service.impl;

import com.fotaleza.fortalezaapi.model.Color;
import com.fotaleza.fortalezaapi.repository.ColorRepository;
import com.fotaleza.fortalezaapi.service.IColorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ColorServiceImpl implements IColorService {

    private final ColorRepository colorRepository;

    @Autowired
    public ColorServiceImpl(ColorRepository colorRepository) {
        this.colorRepository = colorRepository;
    }

    @Override
    public Color saveColor(Color color) {
        return colorRepository.save(color);
    }

    @Override
    public List<Color> getAllActivateColors() {
        return colorRepository
                .findAll()
                .stream()
                .filter(color -> color.getIsActivate().equals(Boolean.TRUE))
                .toList();
    }

    @Override
    public List<Color> getAllInactivateColors() {
        return colorRepository
                .findAll()
                .stream()
                .filter(color -> color.getIsActivate().equals(Boolean.FALSE))
                .toList();
    }

    @Override
    public Color getColorById(Integer colorId) { return colorRepository.findById(colorId).orElse(null); }

    @Override
    public Color getColorByName(String colorName) {
        return colorRepository.findByName(colorName)
                .orElseThrow(() -> new RuntimeException("Color no encontrado con el nombre: " + colorName));
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
