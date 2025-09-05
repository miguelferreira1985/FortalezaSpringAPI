package com.fotaleza.fortalezaapi.controller;

import com.fotaleza.fortalezaapi.service.IPresentationService;
import com.fotaleza.fortalezaapi.service.impl.PresentationServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/presentations")
@RequiredArgsConstructor
public class PresentationController {

    private final IPresentationService presentationService;

    @GetMapping()
    public ResponseEntity<?> getAllPresentations() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(presentationService.getAllPresentations()); }
}
