package com.fotaleza.fortalezaapi.controller;

import com.fotaleza.fortalezaapi.model.Presentation;
import com.fotaleza.fortalezaapi.service.impl.PresentationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/presentation")
public class PresentationController {

    @Autowired
    private PresentationServiceImpl presentationService;

    @GetMapping("/getAllPresentations")
    public List<Presentation> getAllActivatePresentations() { return presentationService.getAllPresentations(); }
}
