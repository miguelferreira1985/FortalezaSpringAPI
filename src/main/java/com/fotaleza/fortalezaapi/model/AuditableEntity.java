package com.fotaleza.fortalezaapi.model;

import com.fotaleza.fortalezaapi.constants.ColumnNames;
import com.fotaleza.fortalezaapi.utils.SecurityUtils;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AuditableEntity {

    @Column(name = ColumnNames.COLUMN_CREATED_DATE_TIME, nullable = false, updatable = false)
    private LocalDateTime createdDateTime;

    @Column(name = ColumnNames.COLUMN_UPDATED_DATE_TIME)
    private LocalDateTime updatedDateTime;

    @Column(name = ColumnNames.COLUMN_CREATED_BY, nullable = false, updatable = false)
    private String createdBy;

    @Column(name = ColumnNames.COLUMN_UPDATED_BY)
    private String updatedBy;

    @Column(name = ColumnNames.COLUMN_IS_ACTIVATE)
    private Boolean isActivate;

    @PrePersist
    protected void onCreate() {
        this.createdDateTime = LocalDateTime.now();
        this.createdBy = SecurityUtils.getCurrentUserLogin().orElse("anonymousUser");
        if (isActivate == null) {
            this.isActivate = true;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedDateTime = LocalDateTime.now();
        this.updatedBy = SecurityUtils.getCurrentUserLogin().orElse("anonymousUser");
    }

}
