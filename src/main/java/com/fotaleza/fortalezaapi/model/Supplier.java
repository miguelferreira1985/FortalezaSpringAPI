package com.fotaleza.fortalezaapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fotaleza.fortalezaapi.constants.ColumnNames;
import com.fotaleza.fortalezaapi.constants.TableNames;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "products")
@EqualsAndHashCode(onlyExplicitlyIncluded = true,callSuper = false)
@Table(name = TableNames.TABLE_SUPPLIERS)
public class Supplier extends AuditableEntity {

    @Id
    @Column(name = ColumnNames.COLUMN_SUPPLIER_ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    @Column(name = ColumnNames.COLUMN_NAME, length = 50, unique = true, nullable = false)
    private String name;

    @Column(name = ColumnNames.COLUMN_CONTACT, length = 50)
    private String contact;

    @Column(name = ColumnNames.COLUMN_LOCATION, length = 20)
    private String location;

    @Column(name = ColumnNames.COLUMN_EMAIL, length = 50)
    private String email;

    @Column(name = ColumnNames.COLUMN_CONTACT_PHONE, length = 11)
    private String contactPhone;

    @Column(name = ColumnNames.COLUMN_OFFICE_PHONE, length = 11)
    private String officePhone;

    @ManyToMany(mappedBy = TableNames.TABLE_SUPPLIERS, fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Product> products = new HashSet<>();

    @Column(name = ColumnNames.COLUMN_IS_ACTIVATE)
    private Boolean isActivate;

    @PrePersist
    protected void onCreate() {
        super.onCreate();
        if (isActivate == null) {
            this.isActivate = true;
        }
    }

}
