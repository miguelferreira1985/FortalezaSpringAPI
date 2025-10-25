package com.fotaleza.fortalezaapi.model;

import com.fotaleza.fortalezaapi.constants.ColumnNames;
import com.fotaleza.fortalezaapi.constants.TableNames;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "subcategory")
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@Table(
        name = TableNames.TABLE_PRODUCTS,
        indexes = {
            @Index(name = "idx_product_name", columnList = ColumnNames.COLUMN_NAME),
                @Index(name = "idx_product_code", columnList = ColumnNames.COLUMN_CODE)
        })
public class Product extends AuditableEntity {

    @Id
    @Column(name = ColumnNames.COLUMN_PRODUCT_ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    @Column(name = ColumnNames.COLUMN_NAME, length = 50, unique = true, nullable = false)
    private String name;

    @Column(name = ColumnNames.COLUMN_CODE, length = 20, unique = true, nullable = false)
    private String code;

    @Column(name = ColumnNames.COLUMN_DESCRIPTION)
    private String description;

    @Column(name = ColumnNames.COLUMN_PRICE, nullable = false)
    private BigDecimal price;

    @Column(name = ColumnNames.COLUMN_COST, nullable = false)
    private BigDecimal cost;

    @Column(name = ColumnNames.COLUMN_STOCK, nullable = false)
    private BigDecimal stock;

    @Column(name = ColumnNames.COLUMN_MINIMUM_STOCK, nullable = false)
    private BigDecimal minimumStock;

    @Column(name = ColumnNames.COlUMN_RECOMMENDED_STOCK, nullable = false)
    private BigDecimal recommendedStock;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = ColumnNames.COLUMN_SUBCATEGORY_ID)
    private Subcategory subcategory;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = ColumnNames.COLUMN_PRESENTATION_ID)
    private Presentation presentation;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<SupplierProduct> supplierProducts = new ArrayList<>();

    @Column(name = ColumnNames.COLUMN_IS_ACTIVATE)
    private Boolean isActivate;

    @PrePersist
    protected void onCreate() {
        super.onCreate();
        if (isActivate == null) {
            this.isActivate = true;
        }
    }

    public void addSupplierProduct(SupplierProduct supplierProduct) {
        supplierProducts.add(supplierProduct);
        supplierProduct.setProduct(this);
    }

    public void clearSupplierProducts() {
        supplierProducts.forEach(sp -> sp.setProduct(null));
        supplierProducts.clear();
    }

    @Transient
    public boolean isBelowOrEqualMinimumStock() {
        return stock != null && minimumStock != null && stock.compareTo(minimumStock) <= 0;
    }

    @Transient
    public BigDecimal getProfitMargin() {
        if (price != null && cost != null) {
            return price.subtract(cost);
        }
        return BigDecimal.ZERO;
    }

    @Transient
    public BigDecimal getProfitValue() {
        if (stock != null && price != null && cost != null) {
            return (price.subtract(cost).multiply(stock));
        }
        return BigDecimal.ZERO;
    }

    @Transient
    public BigDecimal getInventoryValue() {
        if (stock != null && cost != null) {
            return stock.multiply(cost);
        }
        return BigDecimal.ZERO;
    }

}
