package com.agility.shopping.cart.controllers;

import com.agility.shopping.cart.dto.ProductRequest;
import com.agility.shopping.cart.dto.ProductResponse;
import com.agility.shopping.cart.exceptions.CustomError;
import com.agility.shopping.cart.exceptions.ResourceAlreadyExistsException;
import com.agility.shopping.cart.mappers.ProductMapper;
import com.agility.shopping.cart.models.Product;
import com.agility.shopping.cart.repositories.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * This class implement api that relate to Product data
 */
@RestController
@RequestMapping("/products")
@Slf4j
public class ProductController {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ProductRepository productRepository;

    /**
     * Create product
     *
     * @param request Product request
     * @return created product info
     * @throws ResourceAlreadyExistsException if product is already exists
     */
    @PostMapping
    public ProductResponse create(@Valid @RequestBody ProductRequest request) {
        log.debug("POST /products, body={}", request);

        Product product = productRepository.findByName(request.getName());

        if (product != null) {
            throw new ResourceAlreadyExistsException(CustomError.PRODUCT_EXIST);
        }

        // Convert to ProductRequest to Product
        product = productMapper.toProduct(request);

        // Create product
        product = productRepository.save(product);
        log.debug("Response: {}", product);

        // Convert to ProductResponse and return
        return productMapper.toProductResponse(product);
    }
}
