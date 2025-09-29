package com.fotaleza.fortalezaapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fotaleza.fortalezaapi.constants.ColumnNames;
import com.fotaleza.fortalezaapi.constants.TableNames;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = false)
@Table(name = TableNames.TABLE_USERS,
    uniqueConstraints = {
        @UniqueConstraint(columnNames = ColumnNames.COLUMN_USERNAME)
    })
@SQLDelete(sql = "UPDATE users SET isActivate = false WHERE userId = ?")
public class User extends AuditableEntity {

    @Id
    @Column(name = ColumnNames.COLUMN_USER_ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = ColumnNames.COLUMN_USERNAME, length = 50, nullable = false)
    private String username;

    @Column(name = ColumnNames.COLUMN_PASSWORD, nullable = false)
    @JsonIgnore
    private String password;

    @Column(name = ColumnNames.COLUMN_IS_BLOCKED, nullable = false)
    private boolean isBlocked;

    @ToString.Exclude
    @OneToOne(fetch = FetchType.LAZY,
            mappedBy = "user", cascade = CascadeType.PERSIST)
    private Employee employee;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = TableNames.TABLE_USER_ROLES,
                joinColumns = @JoinColumn(name = ColumnNames.COLUMN_USER_ID),
                inverseJoinColumns = @JoinColumn(name = ColumnNames.COLUMN_ROLE_ID))
    private Set<Role> roles = new HashSet<>();

}
