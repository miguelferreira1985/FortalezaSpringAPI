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
    private String companyName;
    private String firstName;
    private String lastName;
    private String address;
    private String email;
    private String phone;
    private String rfc;
    private Boolean isActivate;

    private LocalDateTime createdDateTime;
    private LocalDateTime updatedDateTime;
    private String createdBy;
    private String updatedBy;
}
