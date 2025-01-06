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
@Table(name = TableNames.TABLE_EMPLOYEES,
        uniqueConstraints = {
            @UniqueConstraint(columnNames = ColumnNames.COLUMN_SSN)
        })
public class Employee {

    @Id
    @Column(name = ColumnNames.COLUMN_EMPLOYEE_ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    @Column(name = ColumnNames.COLUMN_FIRST_NAME, length = 50, nullable = false)
    private String firstName;

    @Column(name = ColumnNames.COLUMN_LAST_NAME, length = 50, nullable = false)
    private String lastName;

    @Email
    @Column(name = ColumnNames.COLUMN_EMAIL, length = 50)
    private String email;

    @Column(name = ColumnNames.COLUMN_PHONE, length = 20)
    private String phone;

    @Column(name = ColumnNames.COLUMN_ADDRESS, length = 100)
    private String address;

    @Column(name = ColumnNames.COLUMN_SSN, length = 20, nullable = false)
    private String ssn;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = ColumnNames.COLUMN_USER_ID)
    private User user;

}
