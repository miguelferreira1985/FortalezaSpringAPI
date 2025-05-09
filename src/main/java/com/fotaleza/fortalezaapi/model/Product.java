package com.fotaleza.fortalezaapi.model;

import com.fotaleza.fortalezaapi.constants.ColumnNames;
import com.fotaleza.fortalezaapi.constants.TableNames;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(exclude = {"suppliers", "subcategory"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(
        name = TableNames.TABLE_PRODUCTS,
        indexes = {
            @Index(name = "idx_product_name", columnList = ColumnNames.COLUMN_NAME),
                @Index(name = "idx_product_code", columnList = ColumnNames.COLUMN_CODE)
        })
public class Product {

    @Id
    @Column(name = ColumnNames.COLUMN_PRODUCT_ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    @NotBlank
    @Size(max = 50)
    @Column(name = ColumnNames.COLUMN_NAME, length = 50, unique = true, nullable = false)
    private String name;

    @NotBlank
    @Size(max = 20)
    @Column(name = ColumnNames.COLUMN_CODE, length = 20, unique = true, nullable = false)
    private String code;

    @Column(name = ColumnNames.COLUMN_DESCRIPTION)
    private String description;

    @NotNull
    @DecimalMin("0.0")
    @Column(name = ColumnNames.COLUMN_PRICE, nullable = false)
    private Double price;

    @NotNull
    @DecimalMin("0.0")
    @Column(name = ColumnNames.COLUMN_COST, nullable = false)
    private Double cost;

    @NotNull
    @Min(0)
    @Column(name = ColumnNames.COLUMN_STOCK, nullable = false)
    private Double stock;

    @NotNull
    @Min(0)
    @Column(name = ColumnNames.COLUMN_MINIMUM_STOCK, nullable = false)
    private Double minimumStock;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = ColumnNames.COLUMN_SUBCATEGORY_ID)
    private Subcategory subcategory;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = TableNames.TABLE_PRODUCTS_SUPPLIERS,
                joinColumns = @JoinColumn(name = ColumnNames.COLUMN_PRODUCT_ID),
                inverseJoinColumns = @JoinColumn(name = ColumnNames.COLUMN_SUPPLIER_ID))
    private Set<Supplier> suppliers = new HashSet<>();

    @Column(name = ColumnNames.COLUMN_CREATED_DATE_TIME, updatable = false)
    private LocalDateTime createdDatetime;

    @Column(name = ColumnNames.COLUMN_UPDATED_DATE_TIME)
    private LocalDateTime updatedDatetime;

    @Column(name = ColumnNames.COLUMN_IS_ACTIVATE)
    private Boolean isActivate;

    @PrePersist
    protected void onCreate() {
        this.createdDatetime = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedDatetime = LocalDateTime.now();
    }

}
