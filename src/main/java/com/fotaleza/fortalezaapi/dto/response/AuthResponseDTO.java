package com.fotaleza.fortalezaapi.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class AuthResponseDTO {

    private String token;
    private String refreshToken;
    private String username;
    private List<String> roles;

}
