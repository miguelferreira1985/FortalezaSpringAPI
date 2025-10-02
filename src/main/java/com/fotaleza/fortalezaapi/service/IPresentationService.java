package com.fotaleza.fortalezaapi.service;

import com.fotaleza.fortalezaapi.dto.request.PresentationRequestDTO;
import com.fotaleza.fortalezaapi.dto.response.PresentationResponseDTO;

import java.util.List;

public interface IPresentationService {

    List<PresentationResponseDTO> getAllPresentations();
    PresentationResponseDTO getPresentationById(Integer presentationId);
    PresentationResponseDTO createPresentation(PresentationRequestDTO presentationRequestDTO);
    PresentationResponseDTO updatePresentation(Integer presentation, PresentationRequestDTO presentationRequestDTO);
    void deletePresentation (Integer presentationId);

}
