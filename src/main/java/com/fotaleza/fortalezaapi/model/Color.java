package com.fotaleza.fortalezaapi.model;

import com.fotaleza.fortalezaapi.constants.ColumnNames;
import com.fotaleza.fortalezaapi.constants.TableNames;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = TableNames.TABLE_COLORS)
public class Color {

    @Id
    @Column(name = ColumnNames.COLUMN_COLOR_ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    @NotBlank
    @Size(max = 50)
    @Column(name = ColumnNames.COLUMN_NAME, length = 50, unique = true, nullable = false)
    private String name;

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
