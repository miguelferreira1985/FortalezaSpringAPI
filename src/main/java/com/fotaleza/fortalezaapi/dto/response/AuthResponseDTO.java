package com.fotaleza.fortalezaapi.dto.response;

import lombok.Data;

@Data
public class AuthResponseDTO {

    private String token;
    private String refreshToken;

}
