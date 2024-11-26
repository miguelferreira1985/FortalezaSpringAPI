package com.fotaleza.fortalezaapi.service.impl;

import com.fotaleza.fortalezaapi.model.Category;
import com.fotaleza.fortalezaapi.model.EPresentation;
import com.fotaleza.fortalezaapi.model.Presentation;
import com.fotaleza.fortalezaapi.repository.PresentationRepository;
import com.fotaleza.fortalezaapi.service.IPresentationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PresentationServiceImpl implements IPresentationService {

    @Autowired
    private PresentationRepository presentationRepository;

    @Override
    public Presentation getPresentationByName(EPresentation presentationName) {
        return presentationRepository.findByName(presentationName)
                .orElseThrow(() -> new RuntimeException("Presentacion no encontrada con el nombre: " + presentationName));
    }
}
