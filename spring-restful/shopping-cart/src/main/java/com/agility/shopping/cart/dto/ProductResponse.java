package com.agility.shopping.cart.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

/**
 * This class is used to encapsulate response data in value object when app response
 */
@Getter
@Setter
public class ProductResponse {
    private Long id;
    private String name;
    private String url;
    private Long price;
    private Instant createdAt;
    private Instant updatedAt;
}
