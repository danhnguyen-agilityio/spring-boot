package com.agility.shopping.cart.models;

import com.agility.shopping.cart.dto.Views;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.Instant;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractAuditableEntity<ID> extends AbstractPersistableEntity<ID> {

    @Column(name = "created_at", updatable = false)
    @CreatedDate
    @JsonView(Views.Admin.class)
    private Instant createdAt;

    @Column(name = "updated_at")
    @LastModifiedDate
    @JsonView(Views.Admin.class)
    private Instant updatedAt;
}
