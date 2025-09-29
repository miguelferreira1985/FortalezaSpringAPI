package com.fotaleza.fortalezaapi.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SupplierResponseDTO {

    private Integer id;
    private String name;
    private String contact;
    private String location;
    private String email;
    private String contactPhone;
    private String officePhone;
    private Boolean isActivate;

    private LocalDateTime createdDateTime;
    private LocalDateTime updatedDateTime;
    private String createdBy;
    private String updatedBy;

}
