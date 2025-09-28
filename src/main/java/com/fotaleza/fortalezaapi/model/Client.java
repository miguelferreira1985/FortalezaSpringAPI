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
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@Table(name = TableNames.TABLE_CLIENTS)
public class Client extends AuditableEntity {

    @Id
    @Column(name = ColumnNames.COLUMN_CLIENT_ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    @Column(name = ColumnNames.COLUMN_COMPANY_NAME, length = 50, unique = true, nullable = false)
    private String name;

    @Column(name = ColumnNames.COLUMN_PHONE, length = 20)
    private String phone;

    @Column(name = ColumnNames.COLUMN_RFC, length = 20, unique = true, nullable = false)
    private String rfc;

    @PrePersist
    protected void onCreate() {
        super.onCreate();
        if (this.rfc != null) {
            this.rfc = this.rfc.toUpperCase();
        }
    }

    @PreUpdate
    protected void onUpdate() {
        super.onUpdate();
        if (this.rfc != null) {
            this.rfc = this.rfc.toUpperCase();
        }
    }

}
