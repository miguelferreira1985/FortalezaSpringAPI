package com.fotaleza.fortalezaapi.dto.response;

import com.fotaleza.fortalezaapi.model.Category;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubcategoryResponseDTO {

    private Integer id;
    private String name;
    private String description;
    private Category category;
    private Boolean isActivate;

    private LocalDateTime createdDateTime;
    private LocalDateTime updatedDateTime;
    private String createdBy;
    private String updatedBy;
}
