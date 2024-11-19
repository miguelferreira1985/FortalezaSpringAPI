package com.fotaleza.fortalezaapi.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class UserInfoResponse {

    private Long id;
    private String username;
    private List<String> roles;

}
