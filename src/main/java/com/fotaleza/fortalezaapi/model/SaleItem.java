package com.fotaleza.fortalezaapi.model;

import com.fotaleza.fortalezaapi.constants.ColumnNames;
import com.fotaleza.fortalezaapi.constants.TableNames;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true, exclude = {"product", "sale"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@Table(name = TableNames.TABLE_SALE_ITEMS)
public class SaleItem extends AuditableEntity {

    @Id
    @Column(name = ColumnNames.COLUMN_SALE_ITEM_ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    @Column(name = ColumnNames.COLUMN_QUANTITY, nullable = false)
    private BigDecimal quantity;

    @Column(name = ColumnNames.COLUMN_UNIT_PRICE, nullable = false)
    private BigDecimal unitPrice;

    @Column(name = ColumnNames.COLUMN_SUBTOTAL, nullable = false)
    private BigDecimal subtotal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = ColumnNames.COLUMN_SALE_ID, nullable = false)
    private Sale sale;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = ColumnNames.COLUMN_PRODUCT_ID, nullable = false)
    private Product product;

}
