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
@ToString(exclude = {"category"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@Table(name = TableNames.TABLE_SUBCATEGORIES,
    indexes = {
        @Index(name = "idx_subcategory_name", columnList = ColumnNames.COLUMN_NAME),
            @Index(name = "idx_subcategory_category_name", columnList = ColumnNames.COLUMN_CATEGORY_ID + "," + ColumnNames.COLUMN_NAME)
    })
public class Subcategory extends AuditableEntity {

    @Id
    @Column(name = ColumnNames.COLUMN_SUBCATEGORY_ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    @Column(name = ColumnNames.COLUMN_NAME, length = 50,unique = true,  nullable = false)
    private String name;

    @Column(name = ColumnNames.COLUMN_DESCRIPTION)
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = ColumnNames.COLUMN_CATEGORY_ID, nullable = false)
    private Category category;

}
