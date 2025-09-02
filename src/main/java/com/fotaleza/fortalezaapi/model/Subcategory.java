package com.fotaleza.fortalezaapi.model;

import com.fotaleza.fortalezaapi.constants.ColumnNames;
import com.fotaleza.fortalezaapi.constants.TableNames;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(exclude = {"category", "products"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = TableNames.TABLE_SUBCATEGORIES,
    indexes = {
        @Index(name = "idx_subcategory_name", columnList = ColumnNames.COLUMN_NAME)
    })
public class Subcategory {

    @Id
    @Column(name = ColumnNames.COLUMN_SUBCATEGORY_ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    @Column(name = ColumnNames.COLUMN_NAME, length = 50,unique = true,  nullable = false)
    private String name;

    @Column(name = ColumnNames.COLUMN_DESCRIPTION)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = ColumnNames.COLUMN_CATEGORY_ID, nullable = false)
    private Category category;

    @OneToMany(mappedBy = "subcategory", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Product> products = new HashSet<>();

    @Column(name = ColumnNames.COLUMN_CREATED_DATE_TIME, updatable = false)
    private LocalDateTime createdDateTime;

    @Column(name = ColumnNames.COLUMN_UPDATED_DATE_TIME)
    private LocalDateTime updatedDateTime;

    @Column(name = ColumnNames.COLUMN_IS_ACTIVATE)
    private Boolean isActivate;

    @PrePersist
    protected void onCreate() {
        this.createdDateTime = LocalDateTime.now();
        this.isActivate = true;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedDateTime = LocalDateTime.now();
    }

}
