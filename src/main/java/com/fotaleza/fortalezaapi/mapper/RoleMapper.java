package com.fotaleza.fortalezaapi.mapper;

import com.fotaleza.fortalezaapi.dto.RoleDTO;
import com.fotaleza.fortalezaapi.model.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface RoleMapper {

    RoleDTO toDTO(Role role);

    @Mapping(target = "id", ignore = true)
    Role toEntity(RoleDTO roleDTO);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromDTO(RoleDTO roleDTO, @MappingTarget Role role);

    List<RoleDTO> toDTOList(List<Role> roles);

}
