package com.fotaleza.fortalezaapi.model;

import com.fotaleza.fortalezaapi.constants.ColumnNames;
import com.fotaleza.fortalezaapi.constants.TableNames;
import com.fotaleza.fortalezaapi.enums.EMovementType;
import com.fotaleza.fortalezaapi.utils.SecurityUtils;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString()
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = TableNames.TABLE_INVENTORY_MOVEMENTS)
public class InventoryMovement {

    @Id
    @Column(name = ColumnNames.COLUMN_INVENTORY_MOVEMENT_ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    // Producto afectado
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = ColumnNames.COLUMN_PRODUCT_ID, nullable = false)
    private Product product;

    // Justificación del moviemienti solo se usara par AJUSTE y DEVOLUCIÓN
    @Column(name = ColumnNames.COLUMN_DESCRIPTION)
    private String description;

    // Cantidad del movimiento (positiva = entrada, negativa = salida)
    @Column(name = ColumnNames.COLUMN_QUANTITY, nullable = false)
    private BigDecimal quantity;

    // Stock antes del movimiento
    @Column(name = ColumnNames.COLUMN_PREVIOUS_STOCK)
    private BigDecimal previousStock;

    // Stock después del movimiento
    @Column(name = ColumnNames.COLUMN_NEW_STOCK)
    private BigDecimal newStock;

    // Tipo de movimiento (VENTA, COMPRA, AJUSTE, DEVOLUCIOn, etc.)
    @Enumerated(EnumType.STRING)
    @Column(name = ColumnNames.COLUMN_MOVEMENT_TYPE, length = 20, nullable = false)
    private EMovementType movementType;

    // Usuario que hizo la operación
    @Column(name = ColumnNames.COLUMN_CREATED_BY)
    private String createdBy;

    // Fecha del movimiento
    @Column(name = ColumnNames.COLUMN_MOVEMENT_DATE, nullable = false, updatable = false)
    private LocalDateTime movementDate;

    @PrePersist
    public void prePersist() {
        movementDate = LocalDateTime.now();
        createdBy = SecurityUtils.getCurrentUserLogin().orElse("anonymousUser");
    }
}
