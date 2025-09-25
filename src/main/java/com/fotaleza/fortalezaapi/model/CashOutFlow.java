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
@Table(name = TableNames.TABLE_CASH_OUT_FLOW)
public class CashOutFlow extends AuditableEntity {

    @Id
    @Column(name = ColumnNames.COLUMN_CASH_OUT_FLOW_ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    @Column(name = ColumnNames.COLUMN_AMOUNT, nullable = false)
    private BigDecimal amount;

    @Column(name = ColumnNames.COLUMN_REASON, nullable = false)
    private String reason;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = ColumnNames.COLUMN_CASH_START_ID, nullable = false)
    private CashStart cashStart;

}
