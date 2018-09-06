package com.agility.shopping.cart.mappers;

import com.agility.shopping.cart.dto.ProductRequest;
import com.agility.shopping.cart.dto.ProductResponse;
import com.agility.shopping.cart.models.Product;
import org.mapstruct.Mapper;

/**
 * ProductMapper interface is used to map between different object models
 * (Product, ProductRequest, ProductResponse)
 */
@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductRequest toProductRequest(Product product);

    Product toProduct(ProductRequest productRequest);

    ProductResponse toProductResponse(Product product);

}
