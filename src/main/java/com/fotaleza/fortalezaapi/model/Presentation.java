package com.fotaleza.fortalezaapi.model;

import com.fotaleza.fortalezaapi.constants.ColumnNames;
import com.fotaleza.fortalezaapi.constants.TableNames;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@Table(name = TableNames.TABLE_PRESENTATIONS)
public class Presentation extends AuditableEntity{

    @Id
    @Column(name = ColumnNames.COLUMN_PRESENTATION_ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    @Column(name = ColumnNames.COLUMN_NAME, length = 50, unique = true, nullable = false)
    private String name;

    @Column(name = ColumnNames.COLUMN_ABBREVIATION, length =  10, unique = true, nullable = false)
    private String abbreviation;

}
