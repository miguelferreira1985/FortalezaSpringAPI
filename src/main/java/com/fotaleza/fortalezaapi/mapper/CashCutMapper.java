package com.fotaleza.fortalezaapi.mapper;

import com.fotaleza.fortalezaapi.dto.request.CashCutRequestDTO;
import com.fotaleza.fortalezaapi.dto.response.CashCutResponseDTO;
import com.fotaleza.fortalezaapi.model.CashCut;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CashCutMapper {

    @Mapping(target = "cashStartId", source = "cashStart.id")
    CashCutResponseDTO toResponseDTO(CashCut cashCut);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cashStart", ignore = true)
    @Mapping(target = "startAmount", ignore = true)
    @Mapping(target = "difference", ignore = true)
    @Mapping(target = "createdDateTime", ignore = true)
    @Mapping(target = "updatedDateTime", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    CashCut toEntity(CashCutRequestDTO cashCutRequestDTO);

}
