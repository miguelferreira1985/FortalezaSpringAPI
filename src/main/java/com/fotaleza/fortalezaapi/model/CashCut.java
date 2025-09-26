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
@ToString(callSuper = true, exclude = {"cashStart"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@Table(name = TableNames.TABLE_CASH_CUT)
public class CashCut extends AuditableEntity {

    @Id
    @Column(name = ColumnNames.COLUMN_CASH_CUT_ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = ColumnNames.COLUMN_CASH_START_ID, nullable = false, unique = true)
    private CashStart cashStart;

    @Column(name = ColumnNames.COLUMN_START_AMOUNT, nullable = false)
    private BigDecimal startAmount;

    @Column(name = ColumnNames.COLUMN_FINAL_AMOUNT)
    private BigDecimal finalAmount;

    @Column(name = ColumnNames.COLUMN_DIFFERENCE)
    private BigDecimal difference;

}
