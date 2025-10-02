package com.fotaleza.fortalezaapi.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClientResponseDTO {

    private Integer id;
    private String name;
    private String phone;
    private String rfc;
    private Boolean isActivate;

    private LocalDateTime createdDateTime;
    private LocalDateTime updatedDateTime;
    private String createdBy;
    private String updatedBy;
}
