package com.fotaleza.fortalezaapi.model;

import com.fotaleza.fortalezaapi.constants.ColumnNames;
import com.fotaleza.fortalezaapi.constants.TableNames;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Table(name = TableNames.TABLE_COLORS,
    uniqueConstraints = {
        @UniqueConstraint(columnNames = ColumnNames.COLUMN_NAME)
    })
public class Color {

    @Id
    @Column(name = ColumnNames.COLUMN_COLOR_ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = ColumnNames.COLUMN_NAME, length = 50, nullable = false)
    private String name;

    @Column(name = ColumnNames.COLUMN_CREATED_DATE_TIME)
    private Date createdDateTime;

    @Column(name = ColumnNames.COLUMN_UPDATED_DATE_TIME)
    private Date updatedDateTime;

}
