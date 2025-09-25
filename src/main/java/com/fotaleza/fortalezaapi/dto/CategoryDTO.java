package com.fotaleza.fortalezaapi.dto;

import com.fotaleza.fortalezaapi.enums.ECategory;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO {

    private Integer id;
    private ECategory name;
}
