package com.fotaleza.fortalezaapi.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class SupplierProductId implements Serializable {
    @Column(name = "supplier_id")
    private Integer supplierId;

    @Column(name = "product_id")
    private Integer productId;
}
