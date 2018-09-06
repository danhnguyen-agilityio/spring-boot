package com.agility.shopping.cart.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * This class is used to encapsulate body data in a value object when user request
 */
@Setter
@Getter
@ToString
public class ProductRequest implements Serializable {
    private String name;
    private String url;
    private Long price;
}
