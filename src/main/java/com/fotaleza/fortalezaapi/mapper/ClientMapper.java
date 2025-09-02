package com.fotaleza.fortalezaapi.mapper;

import com.fotaleza.fortalezaapi.dto.ClientDTO;
import com.fotaleza.fortalezaapi.model.Client;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ClientMapper {

    ClientDTO toDTO(Client client);

    @Mapping(target = "id", ignore = true)
    Client toEntity(ClientDTO clientDTO);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromDTO(ClientDTO clientDTO, @MappingTarget Client client);

    List<ClientDTO> toDTOList(List<Client> clients);

}
