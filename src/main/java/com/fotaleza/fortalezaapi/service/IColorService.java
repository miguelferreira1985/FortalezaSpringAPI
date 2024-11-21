package com.fotaleza.fortalezaapi.service;

import com.fotaleza.fortalezaapi.model.Color;

import java.util.List;
import java.util.Optional;

public interface IColorService {

    Color saveColor(Color color);
    List<Color> getAllColors();
    Color getColorById(Integer colorId);
    Optional<Color> getColorByName(String colorName);
    Boolean existsByColorName(String colorName);
    Color updateColor(Color color);
    void deleteColor(Integer colorId);

}
