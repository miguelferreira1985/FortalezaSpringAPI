package com.fotaleza.fortalezaapi.model;

import com.fotaleza.fortalezaapi.constans.ColumnNames;
import com.fotaleza.fortalezaapi.constans.TableNames;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Table(name = TableNames.TABLE_USERS,
    uniqueConstraints = {
        @UniqueConstraint(columnNames = ColumnNames.COLUMN_USERNAME)
    })
public class User {

    @Id
    @Column(name = ColumnNames.COLUMN_USER_ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = ColumnNames.COLUMN_USERNAME, length = 50, nullable = false)
    private String username;

    @Column(name = ColumnNames.COLUMN_FIRST_NAME, length = 50, nullable = false)
    private String firstName;

    @Column(name = ColumnNames.COLUMN_LAST_NAME, length = 50, nullable = false)
    private String lastName;

    @Column(name = ColumnNames.COLUMN_PASSWORD, nullable = false)
    private String password;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = TableNames.TABLE_USER_ROLES,
                joinColumns = @JoinColumn(name = ColumnNames.COLUMN_USER_ID),
                inverseJoinColumns = @JoinColumn(name = ColumnNames.COLUMN_ROLE_ID))
    private Set<Role> roles = new HashSet<>();

}
