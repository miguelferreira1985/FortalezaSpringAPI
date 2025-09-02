package com.fotaleza.fortalezaapi.model;

import com.fotaleza.fortalezaapi.constants.ColumnNames;
import com.fotaleza.fortalezaapi.constants.TableNames;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = TableNames.TABLE_SUPPLIERS)
public class Supplier {

    @Id
    @Column(name = ColumnNames.COLUMN_SUPPLIER_ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    @Column(name = ColumnNames.COLUMN_NAME, length = 50, unique = true, nullable = false)
    private String name;

    @Column(name = ColumnNames.COLUMN_CONTACT, length = 50)
    private String contact;

    @Column(name = ColumnNames.COLUMN_ADDRESS, length = 100)
    private String address;

    @Column(name = ColumnNames.COLUMN_EMAIL, length = 50)
    private String email;

    @Column(name = ColumnNames.COLUMN_PHONE, length = 20)
    private String phone;

    @Column(name = ColumnNames.COLUMN_CREATED_DATE_TIME)
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
