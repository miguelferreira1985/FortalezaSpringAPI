package com.fotaleza.fortalezaapi.model;

import com.fotaleza.fortalezaapi.constants.ColumnNames;
import com.fotaleza.fortalezaapi.constants.TableNames;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true, exclude = {"cashOutFlows"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false )
@Table(name = TableNames.TABLE_CASH_START)
public class CashStart extends AuditableEntity {

    @Id
    @Column(name = ColumnNames.COLUMN_CASH_START_ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    @Column(name = ColumnNames.COLUMN_START_AMOUNT, nullable = false)
    private BigDecimal startAmount;

    @Column(name = ColumnNames.COLUMN_END_DATE_TIME)
    private LocalDateTime endDateTime;

    @Column(name = ColumnNames.COLUMN_FINAL_AMOUNT)
    private BigDecimal finalAmount;

    @Column(name = ColumnNames.COLUMN_DIFFERENCE)
    private BigDecimal difference;

    @OneToMany(mappedBy = "cashStart", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<CashOutFlow> cashOutFlows = new HashSet<>();

}
