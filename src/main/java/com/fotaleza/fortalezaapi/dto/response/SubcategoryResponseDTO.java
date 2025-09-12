package com.fotaleza.fortalezaapi.dto.response;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubcategoryResponseDTO {

    private Integer id;
    private String name;
    private String description;
    private Integer categoryId;
    private Set<Integer> productIds;
    private Boolean isActivate;

    private LocalDateTime createdDateTime;
    private LocalDateTime updatedDateTime;
    private String createdBy;
    private String updatedBy;
}
