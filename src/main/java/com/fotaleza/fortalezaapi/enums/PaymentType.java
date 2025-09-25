package com.fotaleza.fortalezaapi.enums;

public enum PaymentType {

    EFECTIVO,
    TARJETA,
    TRANSFERENCIA;

    public boolean isCashPayment() {
        return this == EFECTIVO;
    }

}
