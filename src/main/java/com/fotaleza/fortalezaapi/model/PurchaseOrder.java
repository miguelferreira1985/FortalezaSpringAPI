package com.fotaleza.fortalezaapi.model;

import com.fotaleza.fortalezaapi.constants.ColumnNames;
import com.fotaleza.fortalezaapi.constants.TableNames;
import com.fotaleza.fortalezaapi.enums.EPurchaseOrderStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@Table(name = TableNames.TABLE_PURCHASE_ORDERS)
public class PurchaseOrder extends AuditableEntity {

    @Id
    @Column(name =  ColumnNames.COLUMN_PURCHASE_ORDER_ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "supplier_id", nullable = false)
    private Supplier supplier;

    @Enumerated(EnumType.STRING)
    @Column(name = ColumnNames.COLUMN_ORDER_STATUS, length = 20, nullable = false)
    private EPurchaseOrderStatus orderStatus;

    @Column(name = ColumnNames.COLUMN_ORDER_DATE, nullable = false)
    private LocalDateTime orderDate;

    @Column(name = ColumnNames.COLUMN_EXPECTED_DELIVERY_DATE)
    private LocalDateTime expectedDeliveryDate;

    @Column(name = ColumnNames.COLUMN_TOTAL_AMOUNT)
    private BigDecimal totalAmount;

    @OneToMany(mappedBy = "purchaseOrder", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PurchaseOrderItem> items = new ArrayList<>();

}
