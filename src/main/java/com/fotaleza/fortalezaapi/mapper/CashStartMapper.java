package com.fotaleza.fortalezaapi.mapper;

import com.fotaleza.fortalezaapi.dto.request.CashStartRequestDTO;
import com.fotaleza.fortalezaapi.dto.response.CashStartResponseDTO;
import com.fotaleza.fortalezaapi.model.CashStart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy =  ReportingPolicy.IGNORE)
public interface CashStartMapper {

    CashStartResponseDTO toResponseDTO(CashStart cashStart);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "endDateTime", ignore = true)
    @Mapping(target = "finalAmount", ignore = true)
    @Mapping(target = "difference", ignore = true)
    @Mapping(target = "cashOutFlows", ignore = true)
    @Mapping(target = "isActivate", ignore = true)
    @Mapping(target = "createdDateTime", ignore = true)
    @Mapping(target = "updatedDateTime", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    CashStart toEntity(CashStartRequestDTO cashStartRequestDTO);

}
