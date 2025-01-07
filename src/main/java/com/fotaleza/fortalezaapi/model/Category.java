package com.fotaleza.fortalezaapi.model;

import com.fotaleza.fortalezaapi.constants.ColumnNames;
import com.fotaleza.fortalezaapi.constants.TableNames;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Table(name = TableNames.TABLE_CATEGORIES,
    uniqueConstraints = {
        @UniqueConstraint(columnNames = ColumnNames.COLUMN_NAME)
    })
public class Category {

    @Id
    @Column(name =  ColumnNames.COLUMN_CATEGORY_ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = ColumnNames.COLUMN_NAME, length = 50, nullable = false)
    private ECategory name;

    @Column(name = ColumnNames.COLUMN_DESCRIPTION)
    private String description;

}
