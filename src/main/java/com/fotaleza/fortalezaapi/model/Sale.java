package com.fotaleza.fortalezaapi.model;

import com.fotaleza.fortalezaapi.constants.ColumnNames;
import com.fotaleza.fortalezaapi.enums.PaymentType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true, exclude = {"saleItems", "cashStart"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@Table
public class Sale extends AuditableEntity {

    @Id
    @Column(name = ColumnNames.COLUMN_SALE_ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = ColumnNames.COLUMN_PAYMENT_TYPE, nullable = false)
    private PaymentType paymentType;

    @Column(name = ColumnNames.COLUMN_TOTAL_AMOUNT)
    private BigDecimal totalAmount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = ColumnNames.COLUMN_CASH_START_ID)
    private CashStart cashStart;

    @OneToMany(mappedBy = "sale", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<SaleItem> saleItems;


}
