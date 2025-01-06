package com.fotaleza.fortalezaapi.service.impl;

import com.fotaleza.fortalezaapi.model.Presentation;
import com.fotaleza.fortalezaapi.repository.PresentationRepository;
import com.fotaleza.fortalezaapi.service.IPresentationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PresentationServiceImpl implements IPresentationService {

    @Autowired
    private PresentationRepository presentationRepository;

    @Override
    public List<Presentation> getAllPresentations() {
        return presentationRepository.findAll().stream().toList();
    }

}
