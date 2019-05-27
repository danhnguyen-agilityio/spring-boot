package com.agility.shopping.cart.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * This class is used to encapsulate body data in a value object when user request
 */
@Setter
@Getter
@ToString
public class ProductRequest implements Serializable {

    @NotBlank
    private String name;

    private String url;

    @NotNull
    private Long price;
}
