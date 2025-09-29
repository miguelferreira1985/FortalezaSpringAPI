package com.fotaleza.fortalezaapi.mapper;

import com.fotaleza.fortalezaapi.dto.request.CashOutFlowRequestDTO;
import com.fotaleza.fortalezaapi.dto.response.CashOutFlowResponseDTO;
import com.fotaleza.fortalezaapi.model.CashOutFlow;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CashOutFlowMapper {

    @Mapping(target = "cashStartId", source = "cashStart.id")
    CashOutFlowResponseDTO toResponseDTO(CashOutFlow cashOutFlow);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cashStart", ignore = true)
    @Mapping(target = "createdDateTime", ignore = true)
    @Mapping(target = "updatedDateTime", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    CashOutFlow toEntity(CashOutFlowRequestDTO cashOutFlowRequestDTO);

}
