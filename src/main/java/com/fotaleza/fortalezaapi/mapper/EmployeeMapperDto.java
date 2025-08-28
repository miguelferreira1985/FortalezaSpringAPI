package com.fotaleza.fortalezaapi.mapper;

import com.fotaleza.fortalezaapi.dto.request.EmployeeRequestDto;
import com.fotaleza.fortalezaapi.dto.response.EmployeeResponseDto;
import com.fotaleza.fortalezaapi.model.Employee;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EmployeeMapperDto {

    public static EmployeeResponseDto toModel(Employee entity) {

        EmployeeResponseDto model = new EmployeeResponseDto();
        model.setEmployeeId(entity.getId());
        model.setFirstName(entity.getFirstName());
        model.setLastName(entity.getLastName());
        model.setEmail(entity.getEmail());
        model.setPhone(entity.getPhone());
        model.setAddress(entity.getAddress());
        model.setSsn(entity.getSsn());
        model.setUsername(entity.getUser().getUsername());
        model.setRoles(entity.getUser().getRoles().stream().toList());
        model.setCreatedDateTime(entity.getCreatedDateTime());
        model.setUpdatedDateTime(entity.getUpdatedDateTime());
        model.setIsActivate(entity.getIsActivate());

        return model;
    }

    public static Employee toEntity(EmployeeRequestDto model) {

        Employee entity = new Employee();
        entity.setFirstName(model.getFirstName());
        entity.setLastName(model.getLastName());
        entity.setEmail(model.getEmail());
        entity.setPhone(model.getPhone());
        entity.setAddress(model.getAddress());
        entity.setSsn(model.getSsn());

        return entity;
    }

    public static List<EmployeeResponseDto> toModelList(List<Employee> entities) {

        List<EmployeeResponseDto> employeeResponseDtoList = new ArrayList<>();

        entities.forEach(entity -> employeeResponseDtoList.add(toModel(entity)));

        return employeeResponseDtoList;
    }

}
