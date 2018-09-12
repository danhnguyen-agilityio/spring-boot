package com.agility.shopping.cart.mappers;

import com.agility.shopping.cart.dto.ProductRequest;
import com.agility.shopping.cart.dto.ProductResponse;
import com.agility.shopping.cart.models.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collection;
import java.util.List;

/**
 * ProductMapper interface is used to map between different object models
 * (Product, ProductRequest, ProductResponse)
 */
@Mapper(componentModel = "spring")
public interface ProductMapper {

    /**
     * Convert product to product request
     *
     * @param product
     * @return product request
     */
    ProductRequest toProductRequest(Product product);

    /**
     * Convert product request to product
     *
     * @param productRequest
     * @return product
     */
    Product toProduct(ProductRequest productRequest);

    /**
     * Convert product to product response
     *
     * @param product
     * @return product response
     */
    ProductResponse toProductResponse(Product product);

    /**
     * Convert list product to list product response
     *
     * @param products List product
     * @return List product response
     */
    List<ProductResponse> toProductResponse(Collection<Product> products);

}
