package com.fotaleza.fortalezaapi.service;

import com.fotaleza.fortalezaapi.model.Color;

import java.util.List;

public interface IColorService {

    Color saveColor(Color color);
    List<Color> getAllColors();
    Color getColorById(Integer colorId);
    Color updateColor(Integer colorId, Color color);
    void deleteColor(Integer colorId);

}
