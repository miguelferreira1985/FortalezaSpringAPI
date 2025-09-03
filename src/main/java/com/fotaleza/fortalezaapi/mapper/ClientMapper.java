package com.fotaleza.fortalezaapi.mapper;

import com.fotaleza.fortalezaapi.dto.ClientRequestDTO;
import com.fotaleza.fortalezaapi.dto.ClientResponseDTO;
import com.fotaleza.fortalezaapi.model.Client;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ClientMapper {

    ClientResponseDTO toResponseDTO(Client client);

    List<ClientResponseDTO> toResponseDTOList(List<Client> clients);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "rfc", expression = "java(clientRequestDTO.getRfc() != null ? clientRequestDTO.getRfc().toUpperCase() : null)")
    Client toEntity(ClientRequestDTO clientRequestDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "rfc", expression = "java(clientRequestDTO.getRfc() != null ? clientRequestDTO.getRfc().toUpperCase() : null)")
    void updateEntityFromRequestDTO(ClientRequestDTO clientRequestDTO, @MappingTarget Client client);

}
