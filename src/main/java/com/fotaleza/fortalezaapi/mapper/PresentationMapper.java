package com.fotaleza.fortalezaapi.mapper;

import com.fotaleza.fortalezaapi.dto.request.PresentationRequestDTO;
import com.fotaleza.fortalezaapi.dto.response.PresentationResponseDTO;
import com.fotaleza.fortalezaapi.model.Presentation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PresentationMapper {

    PresentationResponseDTO toResponseDTO(Presentation presentation);

    @Mapping(target = "id", ignore = true)
    Presentation toEntity(PresentationRequestDTO presentationRequestDTO);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromRequestDTO(PresentationRequestDTO presentationRequestDTO, @MappingTarget Presentation presentation);

    List<PresentationResponseDTO> toResponseDTOList(List<Presentation> presentations);

}
