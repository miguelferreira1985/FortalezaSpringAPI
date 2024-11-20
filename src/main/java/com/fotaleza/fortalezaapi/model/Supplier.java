package com.fotaleza.fortalezaapi.model;

import com.fotaleza.fortalezaapi.constans.ColumnNames;
import com.fotaleza.fortalezaapi.constans.TableNames;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Table(name = TableNames.TABLE_SUPPLIERS,
    uniqueConstraints = {
        @UniqueConstraint(columnNames = ColumnNames.COLUMN_NAME)
    })
public class Supplier {

    @Id
    @Column(name = ColumnNames.COLUMN_SUPPLIER_ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = ColumnNames.COLUMN_NAME, length = 50, nullable = false)
    private String name;

    @Column(name = ColumnNames.COLUMN_CONTACT, length = 50)
    private String contact;

    @Column(name = ColumnNames.COLUMN_ADDRESS, length = 100)
    private String address;

    @Email
    @Column(name = ColumnNames.COLUMN_EMAIL, length = 50)
    private String email;

    @Column(name = ColumnNames.COLUMN_PHONE, length = 20)
    private String phone;
}
