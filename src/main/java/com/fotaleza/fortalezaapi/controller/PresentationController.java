package com.fotaleza.fortalezaapi.controller;

import com.fotaleza.fortalezaapi.dto.request.PresentationRequestDTO;
import com.fotaleza.fortalezaapi.dto.response.PresentationResponseDTO;
import com.fotaleza.fortalezaapi.service.IPresentationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/presentations")
@RequiredArgsConstructor
public class PresentationController {

    private final IPresentationService presentationService;

    @PostMapping
    public ResponseEntity<PresentationResponseDTO> createPresentation(@Valid @RequestBody PresentationRequestDTO presentationRequestDTO) {
        PresentationResponseDTO createdPresentation = presentationService.createPresentation(presentationRequestDTO);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdPresentation.getId())
                .toUri();
        return ResponseEntity.created(location).body(createdPresentation);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PresentationResponseDTO> updatePresentation(@PathVariable Integer id, @Valid @RequestBody PresentationRequestDTO presentationRequestDTO) {
        PresentationResponseDTO updatedPresentation = presentationService.updatePresentation(id, presentationRequestDTO);
        return ResponseEntity.ok(updatedPresentation);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PresentationResponseDTO> getPresentationById(@PathVariable Integer id) {
        PresentationResponseDTO presentation = presentationService.getPresentationById(id);
        return ResponseEntity.ok(presentation);
    }

    @GetMapping()
    public ResponseEntity<List<PresentationResponseDTO>> getAllPresentations() {
        List<PresentationResponseDTO> presentations = presentationService.getAllPresentations();
        return ResponseEntity.ok(presentations);
    }
}
