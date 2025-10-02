package com.fotaleza.fortalezaapi.enums;

public enum EMovementType {
    VENTA, // Reduce stock
    COMPRA, // Aumenta el stock
    DEVOLUCION, // Devolución de un cliente aumenta el stokc
    AJUSTE, // Ajuste Manual
}
