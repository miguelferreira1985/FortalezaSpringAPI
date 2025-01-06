package com.fotaleza.fortalezaapi.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ColorRequestDto {

    private Integer id;

    @NotBlank
    @Size(min = 3, max = 50)
    private String name;

}
