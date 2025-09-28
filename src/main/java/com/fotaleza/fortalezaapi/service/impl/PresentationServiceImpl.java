package com.fotaleza.fortalezaapi.service.impl;

import com.fotaleza.fortalezaapi.dto.request.PresentationRequestDTO;
import com.fotaleza.fortalezaapi.dto.response.PresentationResponseDTO;
import com.fotaleza.fortalezaapi.exception.ResourceAlreadyExistsException;
import com.fotaleza.fortalezaapi.exception.ResourceNotFoundException;
import com.fotaleza.fortalezaapi.mapper.PresentationMapper;
import com.fotaleza.fortalezaapi.model.Presentation;
import com.fotaleza.fortalezaapi.repository.PresentationRepository;
import com.fotaleza.fortalezaapi.service.IPresentationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PresentationServiceImpl implements IPresentationService {

    private final PresentationRepository presentationRepository;
    private final PresentationMapper presentationMapper;

    @Override
    @Transactional
    public PresentationResponseDTO createPresentation(PresentationRequestDTO presentationRequestDTO) {

        validateNameAndAbbreviationUnique(presentationRequestDTO.getName(), presentationRequestDTO.getAbbreviation(), null);

        Presentation presentation = presentationMapper.toEntity(presentationRequestDTO);
        Presentation savedPresentation = presentationRepository.save(presentation);

        return presentationMapper.toResponseDTO(savedPresentation);
    }

    @Override
    @Transactional
    public PresentationResponseDTO updatePresentation(Integer presentationId, PresentationRequestDTO presentationRequestDTO) {
        Presentation presentationToUpdate = presentationRepository.findById(presentationId)
                .orElseThrow(() -> new ResourceNotFoundException("La presentación que desea actualizar no existe."));

        validateNameAndAbbreviationUnique(presentationRequestDTO.getName(), presentationRequestDTO.getAbbreviation(), presentationId);

        presentationMapper.updateEntityFromRequestDTO(presentationRequestDTO, presentationToUpdate);

        Presentation updatedPresentation = presentationRepository.save(presentationToUpdate);

        return presentationMapper.toResponseDTO(updatedPresentation);
    }

    @Override
    @Transactional
    public void deletePresentation (Integer presentationId) {
        Presentation presentationToDelete = presentationRepository.findById(presentationId)
                .orElseThrow(() -> new ResourceNotFoundException("La presentación que desea eliminar no existe."));

        presentationRepository.deleteById(presentationId);
    }

    @Override
    public PresentationResponseDTO getPresentationById(Integer presentationId) {
        Presentation presentation = presentationRepository.findById(presentationId)
                .orElseThrow(() -> new ResourceNotFoundException("La presentación  no existe."));
        return presentationMapper.toResponseDTO(presentation);
    }

    @Override
    public List<PresentationResponseDTO> getAllPresentations() {
        List<Presentation> presentations = presentationRepository.findAll()
                .stream().toList();

        return presentationMapper.toResponseDTOList(presentations);
    }

    private void validateNameAndAbbreviationUnique(String name, String abbreviation, Integer presentationId) {

        if (presentationId == null) {
            presentationRepository.findByNameOrAbbreviation(name, abbreviation)
                    .ifPresent(p -> {
                        throw new ResourceAlreadyExistsException(String.format("Ya existe una presentación con el nombre %s o con la abreviación %s.", name, abbreviation));
                    });
        } else {
            presentationRepository.findByNameOrAbbreviationAndIdNot(name, abbreviation, presentationId)
                    .ifPresent(p -> {
                        throw new ResourceAlreadyExistsException(String.format("Ya existe una presentación con el nombre %s o con la abreviación %s.", name, abbreviation));
                    });
        }
    }

}
