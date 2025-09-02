package com.fotaleza.fortalezaapi.model;

import com.fotaleza.fortalezaapi.constants.ColumnNames;
import com.fotaleza.fortalezaapi.constants.TableNames;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;

import java.time.LocalDateTime;
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
@SQLDelete(sql = "UPDATE users SET isActivate = false WHERE userId = ?")
public class User {

    @Id
    @Column(name = ColumnNames.COLUMN_USER_ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = ColumnNames.COLUMN_USERNAME, length = 50, nullable = false)
    private String username;

    @Column(name = ColumnNames.COLUMN_PASSWORD, nullable = false)
    private String password;

    @ToString.Exclude
    @OneToOne(fetch = FetchType.LAZY,
            mappedBy = "user", cascade = CascadeType.PERSIST)
    private Employee employee;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = TableNames.TABLE_USER_ROLES,
                joinColumns = @JoinColumn(name = ColumnNames.COLUMN_USER_ID),
                inverseJoinColumns = @JoinColumn(name = ColumnNames.COLUMN_ROLE_ID))
    private Set<Role> roles = new HashSet<>();

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
