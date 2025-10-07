package com.fotaleza.fortalezaapi.model;

import com.fotaleza.fortalezaapi.constants.ColumnNames;
import com.fotaleza.fortalezaapi.constants.TableNames;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@Table(name = TableNames.TBALE_PURCHSE_ORDER_ITEMS)
public class PurchaseOrderItem {

    @Id
    @Column(name =  ColumnNames.COLUMN_PURCHASE_ORDER_ITEM_ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = ColumnNames.COLUMN_PURCHASE_ORDER_ID, nullable = false)
    private PurchaseOrder purchaseOrder;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = ColumnNames.COLUMN_PRODUCT_ID, nullable = false)
    private Product product;

    @Column(name = ColumnNames.COLUMN_QUANTITY_ORDERED, precision = 12, scale = 2)
    private BigDecimal quantityOrdered;

    @Column(name = ColumnNames.COLUMN_QUANTITY_RECEIVED, precision = 12, scale = 2)
    private BigDecimal quantityReceived = BigDecimal.ZERO;

    @Column(name = ColumnNames.COLUMN_UNIT_COST, precision = 12, scale = 2)
    private BigDecimal unitCost;

    @Column(name = ColumnNames.COLUMN_SUBTOTAL, precision = 12, scale = 2)
    private BigDecimal subtotal;

}
