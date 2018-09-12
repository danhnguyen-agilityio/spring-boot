package com.agility.shopping.cart.controllers;

import com.agility.shopping.cart.dto.ProductRequest;
import com.agility.shopping.cart.dto.ProductResponse;
import com.agility.shopping.cart.exceptions.CustomError;
import com.agility.shopping.cart.exceptions.ResourceAlreadyExistsException;
import com.agility.shopping.cart.exceptions.ResourceNotFoundException;
import com.agility.shopping.cart.mappers.ProductMapper;
import com.agility.shopping.cart.models.Product;
import com.agility.shopping.cart.repositories.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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

        boolean existedName = productRepository.existsByName(request.getName());

        if (existedName) {
            throw new ResourceAlreadyExistsException(CustomError.PRODUCT_EXIST);
        }

        // Convert to ProductRequest to Product
        Product product = productMapper.toProduct(request);

        // Create product
        product = productRepository.save(product);
        log.debug("Response: {}", product);

        // Convert to ProductResponse and return
        return productMapper.toProductResponse(product);
    }

    /**
     * Get all product
     *
     * @return List product
     */
    @GetMapping
    public List<ProductResponse> findAll() {
        List<Product> products = productRepository.findAll();
        return productMapper.toProductResponse(products);
    }

    /**
     * Get product by given id
     *
     * @param id Id of product
     * @return Product with given id
     * @throws ResourceNotFoundException if product doesn't exist with given id
     */
    @GetMapping(value = "/{id}")
    public ProductResponse findOne(@PathVariable long id) {
        Product product = productRepository.findOne(id);

        // Throw exception when null product
        if (product == null) {
            throw new ResourceNotFoundException(CustomError.PRODUCT_NOT_FOUND);
        }

        return productMapper.toProductResponse(product);
    }

    /**
     * Update product by given id
     *
     * @param id Product id
     * @param request Product request
     * @return Product response
     * @throws ResourceNotFoundException if product with given id not exist
     * @throws ResourceAlreadyExistsException if new name in request exists in other product
     */
    @PutMapping(value = "/{id}")
    public ProductResponse update(@PathVariable long id, @Valid @RequestBody ProductRequest request) {

        // Get product by id
        Product product = productRepository.findOne(id);

        // Throw resource not found exception when product not exist
        if (product == null) {
            throw new ResourceNotFoundException(CustomError.PRODUCT_NOT_FOUND);
        }

        // Set new info and update product if product contain new name
        if (product.getName() == request.getName()) {
            product.setPrice(request.getPrice());
            product.setUrl(request.getUrl());
            product = productRepository.save(product);
            return productMapper.toProductResponse(product);
        }

        // Throw resource exists exception when new name exist
        boolean existedName = productRepository.existsByName(request.getName());
        if (existedName) {
            throw new ResourceAlreadyExistsException((CustomError.PRODUCT_EXIST));
        }

        // Set new info and update product when new name not exist
        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setUrl(request.getUrl());
        product = productRepository.save(product);
        return productMapper.toProductResponse(product);
    }


    /**
     * Delete product by given id
     *
     * @param id Id of product
     * @return Deleted product with given id
     * @throws ResourceNotFoundException if product doesn't exist with given id
     */
    @DeleteMapping(value = "/{id}")
    public ProductResponse delete(@PathVariable long id) {
        Product product = productRepository.findOne(id);

        // Throw exception when null product
        if (product == null) {
            throw new ResourceNotFoundException(CustomError.PRODUCT_NOT_FOUND);
        }

        // Delete product
        productRepository.delete(id);

        return productMapper.toProductResponse(product);
    }
}
