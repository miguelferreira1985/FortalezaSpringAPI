package com.fotaleza.fortalezaapi.dto.response;

import lombok.Data;

@Data
public class AuthResponseDto {

    private String token;
    private String refreshToken;

}
