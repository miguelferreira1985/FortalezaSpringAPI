package com.fotaleza.fortalezaapi.controller;

import com.fotaleza.fortalezaapi.dto.request.PresentationRequestDTO;
import com.fotaleza.fortalezaapi.dto.response.ApiResponse;
import com.fotaleza.fortalezaapi.dto.response.PresentationResponseDTO;
import com.fotaleza.fortalezaapi.service.IPresentationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/presentations")
@RequiredArgsConstructor
public class PresentationController {

    private final IPresentationService presentationService;

    @PostMapping
    public ResponseEntity<ApiResponse<PresentationResponseDTO>> createPresentation(@Valid @RequestBody PresentationRequestDTO presentationRequestDTO) {

        PresentationResponseDTO createdPresentation = presentationService.createPresentation(presentationRequestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.<PresentationResponseDTO>builder()
                        .status(HttpStatus.CREATED.value())
                        .message(String.format("La presentación %s ha sido agregada", createdPresentation.getName().toUpperCase()))
                        .data(createdPresentation)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<PresentationResponseDTO>> updatePresentation(@PathVariable Integer id, @Valid @RequestBody PresentationRequestDTO presentationRequestDTO) {

        PresentationResponseDTO updatedPresentation = presentationService.updatePresentation(id, presentationRequestDTO);

        return ResponseEntity.ok(
                ApiResponse.<PresentationResponseDTO>builder()
                        .status(HttpStatus.OK.value())
                        .message(String.format("La presentación %s ha sido actualizada", updatedPresentation.getName().toUpperCase()))
                        .data(updatedPresentation)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PresentationResponseDTO>> getPresentationById(@PathVariable Integer id) {

        PresentationResponseDTO presentation = presentationService.getPresentationById(id);

        return ResponseEntity.ok(
                ApiResponse.<PresentationResponseDTO>builder()
                        .status(HttpStatus.OK.value())
                        .message("Presentación obtenida existosamente.")
                        .data(presentation)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @GetMapping()
    public ResponseEntity<ApiResponse<List<PresentationResponseDTO>>> getAllPresentations() {

        List<PresentationResponseDTO> presentations = presentationService.getAllPresentations();

        return ResponseEntity.ok(
                ApiResponse.<List<PresentationResponseDTO>>builder()
                        .status(HttpStatus.OK.value())
                        .message("Presentaciones obtenidas existosamente.")
                        .data(presentations)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }
}
