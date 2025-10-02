package com.fotaleza.fortalezaapi.mapper;

import com.fotaleza.fortalezaapi.dto.request.EmployeeRequestDTO;
import com.fotaleza.fortalezaapi.dto.response.EmployeeResponseDTO;
import com.fotaleza.fortalezaapi.dto.UserDataDTO;
import com.fotaleza.fortalezaapi.model.Employee;
import com.fotaleza.fortalezaapi.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface EmployeeMapper {

    @Mapping(target = "userId", source = "user.id")
    EmployeeResponseDTO toResponseDTO(Employee employee);

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "isActivate", ignore = true)
    @Mapping(target = "createdDateTime", ignore = true)
    @Mapping(target = "updatedDateTime", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    Employee toEntity(EmployeeRequestDTO employeeRequestDTODTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "isActivate", ignore = true)
    @Mapping(target = "createdDateTime", ignore = true)
    @Mapping(target = "updatedDateTime", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    void updateEntityFromRequestDTO(EmployeeRequestDTO employeeDTO, @MappingTarget Employee employee);

    List<EmployeeResponseDTO> toResponseDTOList(List<Employee> employees);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "roles", ignore = true)
    User toUserEntity(UserDataDTO userDataDTO);
}
