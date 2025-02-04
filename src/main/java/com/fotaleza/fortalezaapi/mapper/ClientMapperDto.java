package com.fotaleza.fortalezaapi.mapper;

import com.fotaleza.fortalezaapi.dto.request.ClientRequestDto;
import com.fotaleza.fortalezaapi.dto.response.ClientResponseDto;
import com.fotaleza.fortalezaapi.model.Client;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ClientMapperDto {

    public static ClientResponseDto toModel(Client entity) {

        ClientResponseDto model = new ClientResponseDto();

        model.setId(entity.getId());
        model.setFirstName(entity.getFirstName());
        model.setLastName(entity.getLastName());
        model.setAddress(entity.getAddress());
        model.setEmail(entity.getEmail());
        model.setPhone(entity.getPhone());
        model.setRfc(entity.getRfc());
        model.setCreatedDateTime(entity.getCreatedDateTime());
        model.setUpdatedDateTime(entity.getUpdatedDateTime());
        model.setIsActivate(entity.getIsActivate());

        return model;
    }

    public static Client toEntity(ClientRequestDto model) {

        Client entity = new Client();
        entity.setId(model.getId());
        entity.setCompanyName(model.getCompanyName());
        entity.setFirstName(model.getFirstName());
        entity.setLastName(model.getLastName());
        entity.setAddress(model.getAddress());
        entity.setEmail(model.getEmail());
        entity.setPhone(model.getPhone());
        entity.setRfc(model.getRfc());

        return entity;
    }

    public static List<ClientResponseDto> toModelList(List<Client> entities) {

        List<ClientResponseDto> clientResponseDtoList = new ArrayList<>();

        entities.forEach(entity -> clientResponseDtoList.add(toModel(entity)));

        return clientResponseDtoList;
    }

}
