package com.fotaleza.fortalezaapi.model;

import com.fotaleza.fortalezaapi.constants.ColumnNames;
import com.fotaleza.fortalezaapi.constants.TableNames;
import com.fotaleza.fortalezaapi.enums.EPresentation;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = TableNames.TABLE_PRESENTATIONS)
public class Presentation {

    @Id
    @Column(name = ColumnNames.COLUMN_PRESENTATION_ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = ColumnNames.COLUMN_NAME, length = 50, unique = true, nullable = false)
    private EPresentation name;

}
