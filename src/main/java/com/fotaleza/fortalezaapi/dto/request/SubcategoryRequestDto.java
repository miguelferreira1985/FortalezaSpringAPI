package com.fotaleza.fortalezaapi.dto.request;

import com.fotaleza.fortalezaapi.model.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubcategoryRequestDto {

    private Integer id;

    @NotBlank
    @Size(min = 3, max = 50)
    private String name;

    @Size(min = 3, max = 200)
    private String description;
    private Category category;

}
