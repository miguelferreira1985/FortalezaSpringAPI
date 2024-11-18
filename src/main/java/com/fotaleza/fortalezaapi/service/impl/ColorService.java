package com.fotaleza.fortalezaapi.service.impl;

import com.fotaleza.fortalezaapi.model.Color;
import com.fotaleza.fortalezaapi.repository.ColorRepository;
import com.fotaleza.fortalezaapi.service.IColorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ColorService implements IColorService {

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
    public Color updateColor(Integer colorId, Color color) {

        Color existingColor = colorRepository.findById(colorId).orElse(null);

        if(existingColor != null) {
            existingColor.setName(color.getName());
        }

        return existingColor;
    }

    @Override
    public void deleteColor(Integer colorId) {
        colorRepository.deleteById(colorId);
    }
}
