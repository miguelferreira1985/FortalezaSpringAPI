package com.fotaleza.fortalezaapi.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class MessageResponse {

    private String message;
    private Object entity;

}
