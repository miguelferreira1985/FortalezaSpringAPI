package com.fotaleza.fortalezaapi.service;

import com.fotaleza.fortalezaapi.model.Color;

import java.util.List;

public interface IColorService {

    Color saveColor(Color color);
    List<Color> getAllColors(Boolean isActivate);
    Color getColorById(Integer colorId);
    Color getColorByName(String colorName);
    Boolean existsByColorName(String colorName);
    Color updateColor(Color color);
    void deleteColor(Integer colorId);

}
