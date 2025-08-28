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
@Table(name = TableNames.TABLE_CLIENTS)
public class Client {

    @Id
    @Column(name = ColumnNames.COLUMN_CLIENT_ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    @Size(max = 50)
    @Column(name = ColumnNames.COLUMN_COMPANY_NAME, length = 50)
    private String companyName;

    @NotBlank
    @Size(max = 50)
    @Column(name = ColumnNames.COLUMN_FIRST_NAME, length = 50, nullable = false)
    private String firstName;

    @NotBlank
    @Size(max = 50)
    @Column(name = ColumnNames.COLUMN_LAST_NAME, length = 50, nullable = false)
    private String lastName;

    @Size(max = 100)
    @Column(name = ColumnNames.COLUMN_ADDRESS, length = 100)
    private String address;

    @Email
    @Column(name = ColumnNames.COLUMN_EMAIL, length = 50)
    private String email;

    @Column(name = ColumnNames.COLUMN_PHONE, length = 20)
    private String phone;

    @NotBlank
    @Size(max = 20)
    @Column(name = ColumnNames.COLUMN_RFC, length = 20, unique = true, nullable = false)
    private String rfc;

    @Column(name = ColumnNames.COLUMN_CREATED_DATE_TIME)
    private LocalDateTime createdDateTime;

    @Column(name = ColumnNames.COLUMN_UPDATED_DATE_TIME)
    private LocalDateTime updatedDateTime;

    @Column(name = ColumnNames.COLUMN_IS_ACTIVATE)
    private Boolean isActivate;

    @PrePersist
    protected void onCreate() {
        this.createdDateTime = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedDateTime = LocalDateTime.now();
    }
}
