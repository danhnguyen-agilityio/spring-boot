package com.agility.shopping.cart.models;

import com.agility.shopping.cart.dto.Views;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.*;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.time.Instant;
import java.util.Set;

/**
 * Product entity class
 */
@Entity
@Table(name = "product")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Product extends AbstractAuditableEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    @JsonView(Views.Member.class)
    private Long id;

    @Column(name = "name", unique = true)
    @NotBlank
    @JsonView(Views.Member.class)
    private String name;

    @URL
    @JsonView(Views.Member.class)
    private String url;

    @Column(name = "price", nullable = false)
    @Min(0)
    @JsonView(Views.Member.class)
    private Long price;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<CartItem> cartItems;
}
