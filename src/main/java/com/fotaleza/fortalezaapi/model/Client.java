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
@Table(name = TableNames.TABLE_CLIENTS)
public class Client {

    @Id
    @Column(name = ColumnNames.COLUMN_CLIENT_ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = ColumnNames.COLUMN_COMPANY_NAME, length = 50)
    private String companyName;

    @Column(name = ColumnNames.COLUMN_FIRST_NAME, length = 50, nullable = false)
    private String firstName;

    @Column(name = ColumnNames.COLUMN_LAST_NAME, length = 50, nullable = false)
    private String lastName;

    @Column(name = ColumnNames.COLUMN_ADDRESS, length = 100)
    private String address;

    @Email
    @Column(name = ColumnNames.COLUMN_EMAIL, length = 50)
    private String email;

    @Column(name = ColumnNames.COLUMN_PHONE, length = 20)
    private String phone;

    @Column(name = ColumnNames.COLUMN_RFC, length = 20)
    private String rfc;

}
