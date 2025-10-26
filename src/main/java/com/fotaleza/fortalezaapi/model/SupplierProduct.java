package com.fotaleza.fortalezaapi.model;

import com.fotaleza.fortalezaapi.constants.ColumnNames;
import com.fotaleza.fortalezaapi.constants.TableNames;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"product", "supplier"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true,callSuper = false)
@Table(name = TableNames.TABLE_SUPPLIER_PRODUCTS)
public class SupplierProduct implements Serializable {

    @EmbeddedId
    private SupplierProductId id = new SupplierProductId();

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("productId")
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("supplierId")
    @JoinColumn(name = "supplier_id", nullable = false)
    private Supplier supplier;

    @Column(name = ColumnNames.COLUMN_COST, nullable = false)
    private BigDecimal cost;

    @Column(name = ColumnNames.COLUMN_DISCOUNT)
    private BigDecimal discount;

    @Column(name = ColumnNames.COLUMN_UPDATED_DATE_TIME, nullable = false)
    private LocalDateTime lastUpdated = LocalDateTime.now();

    @PrePersist
    @PreUpdate
    public void preSave() {
        this.lastUpdated = LocalDateTime.now();
    }

}
