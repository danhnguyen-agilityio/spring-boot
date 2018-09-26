package com.agility.shopping.cart.controllers;

import com.agility.shopping.cart.models.Product;
import com.agility.shopping.cart.models.RequestInfo;
import lombok.extern.slf4j.Slf4j;

import static com.agility.shopping.cart.exceptions.CustomError.PRODUCT_EXIST;
import static com.agility.shopping.cart.exceptions.CustomError.PRODUCT_NOT_FOUND;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

/**
 * This class test RESTful api for product
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ProductControllerTest extends BaseControllerTest {

    private static final String PRODUCT_ENDPOINT = "/products";
    private static final String PRODUCT_DETAIL_ENDPOINT = "/products/{id}";

    // --------------------------------------------------------------------
    // Create product
    // --------------------------------------------------------------------

    /**
     * Test create product throw forbidden exception when member user access
     */
    @Test
    public void testCreateProductShouldThrowForbiddenWhenMemberUserAccess() throws Exception {
        testResponseData(RequestInfo.builder()
            .request(post(PRODUCT_ENDPOINT))
            .token(memberToken)
            .body(productRequest)
            .httpStatus(HttpStatus.FORBIDDEN)
            .build());
    }

    /**
     * Test create product should throw resource exists exception when product exists
     */
    @Test
    public void testCreateProductShouldThrowResourceExistsWhenProductExists() throws Exception {
        // Mock method
        when(productRepository.existsByName(productRequest.getName())).thenReturn(true);

        testResponseData(RequestInfo.builder()
            .request(post(PRODUCT_ENDPOINT))
            .token(adminToken)
            .body(productRequest)
            .httpStatus(HttpStatus.CONFLICT)
            .jsonMap(createJsonMapError(PRODUCT_EXIST))
            .build());
    }

    /**
     * Test create product should correct
     */
    @Test
    public void testCreateProductShouldCorrect() throws Exception {
        when(productRepository.existsByName(productRequest.getName())).thenReturn(false);
        when(productRepository.save(any(Product.class))).thenReturn(product);

        testResponseData(RequestInfo.builder()
            .request(post(PRODUCT_ENDPOINT))
            .token(adminToken)
            .body(productRequest)
            .httpStatus(HttpStatus.OK)
            .build());
    }

    // --------------------------------------------------------------------
    // Find all product
    // --------------------------------------------------------------------

    /**
     * Test find all product
     */
    // TODO:: How to test with multiple role
    @Test
    public void testFindAllProduct() throws Exception {
        // Mock method
        when(productRepository.findAll()).thenReturn(products);

        testResponseData(RequestInfo.builder()
            .request(get(PRODUCT_ENDPOINT))
            .token(memberToken)
            .httpStatus(HttpStatus.OK)
            .build());
    }

    // --------------------------------------------------------------------
    // Find one product
    // --------------------------------------------------------------------

    /**
     * Test find product should throw not found exception when product not found
     */
    @Test
    public void testFindProductShouldThrowNotFoundWhenProductIdNotFound() throws Exception {
        when(productRepository.findOne(product.getId())).thenReturn(null);

        testResponseData(RequestInfo.builder()
            .request(get(PRODUCT_DETAIL_ENDPOINT, product.getId()))
            .token(memberToken)
            .httpStatus(HttpStatus.NOT_FOUND)
            .build());
    }

    /**
     * Test find product success
     */
    @Test
    public void testFindProductShouldCorrect() throws Exception {
        // Mock method
        when(productRepository.findOne(product.getId())).thenReturn(product);

        testResponseData(RequestInfo.builder()
            .request(get(PRODUCT_DETAIL_ENDPOINT, product.getId()))
            .token(memberToken)
            .httpStatus(HttpStatus.OK)
            .build());
    }

    // ================================================
    // Test update product
    // ================================================

    /**
     * Test update product throw forbidden exception when member user access
     */
    @Test
    public void testUpdateProductShouldThrowForbiddenWhenMemberUserAccess() throws Exception {
        testResponseData(RequestInfo.builder()
            .request(put(PRODUCT_DETAIL_ENDPOINT, product.getId()))
            .token(memberToken)
            .body(productRequest)
            .httpStatus(HttpStatus.FORBIDDEN)
            .build());
    }

    /***
     * Test update product throw resource not found exception when product not found
     */
    @Test
    public void testUpdateProductShouldThrowResourceNotFoundExceptionWhenProductNotFound() throws Exception {
        // Mock method
        when(productRepository.findOne(product.getId())).thenReturn(null);

        testResponseData(RequestInfo.builder()
            .request(put(PRODUCT_DETAIL_ENDPOINT, product.getId()))
            .token(adminToken)
            .body(productRequest)
            .httpStatus(HttpStatus.NOT_FOUND)
            .jsonMap(createJsonMapError(PRODUCT_NOT_FOUND))
            .build());
    }

    /***
     * Test update product should success when product not update name
     */
    @Test
    public void testUpdateProductShouldSuccessWhenProductNotUpdateName() throws Exception {
        product.setName(productRequest.getName());
        when(productRepository.findOne(product.getId())).thenReturn(product);
        when(productRepository.save(product)).thenReturn(product);

        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("$.url", productRequest.getUrl());
        testResponseData(RequestInfo.builder()
            .request(put(PRODUCT_DETAIL_ENDPOINT, product.getId()))
            .token(adminToken)
            .body(productRequest)
            .httpStatus(HttpStatus.OK)
            .jsonMap(jsonMap)
            .build());
    }

    /***
     * Test update product throw resource exists exception when new name exists
     */
    @Test
    public void testUpdateProductShouldThrowResourceExistsExceptionWhenNewNameExists() throws Exception {
        // Mock method
        when(productRepository.findOne(product.getId())).thenReturn(product);
        when(productRepository.existsByName(productRequest.getName())).thenReturn(true);

        testResponseData(RequestInfo.builder()
            .request(put(PRODUCT_DETAIL_ENDPOINT, product.getId()))
            .token(adminToken)
            .body(productRequest)
            .httpStatus(HttpStatus.CONFLICT)
            .build());
    }

    /***
     * Test update product should correct when new name not exist
     */
    @Test
    public void testUpdateProductShouldCorrectWhenNewNameNotExist() throws Exception {
        // Mock method
        when(productRepository.findOne(product.getId())).thenReturn(product);
        when(productRepository.existsByName(productRequest.getName())).thenReturn(false);
        when(productRepository.save(product)).thenReturn(product);

        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("$.name", productRequest.getName());
        jsonMap.put("$.url", productRequest.getUrl());
        testResponseData(RequestInfo.builder()
            .request(put(PRODUCT_DETAIL_ENDPOINT, product.getId()))
            .token(adminToken)
            .body(productRequest)
            .httpStatus(HttpStatus.OK)
            .jsonMap(jsonMap)
            .build());
    }

    // ================================================
    // Test delete product
    // ================================================

    /**
     * Test delete product throw forbidden exception when member user access
     */
    @Test
    public void testDeleteProductShouldThrowForbiddenWhenMemberUserAccess() throws Exception {
        testResponseData(RequestInfo.builder()
            .request(delete(PRODUCT_DETAIL_ENDPOINT, product.getId()))
            .token(memberToken)
            .httpStatus(HttpStatus.FORBIDDEN)
            .build());
    }

    /**
     * Test delete product should throw not found exception when product not found
     */
    @Test
    public void testDeleteProductShouldThrowNotFoundWhenProductNotFound() throws Exception {
        // Mock method
        when(productRepository.findOne(product.getId())).thenReturn(null);

        testResponseData(RequestInfo.builder()
            .request(delete(PRODUCT_DETAIL_ENDPOINT, product.getId()))
            .token(adminToken)
            .httpStatus(HttpStatus.NOT_FOUND)
            .build());
    }

    /**
     * Test delete product should correct
     */
    @Test
    public void testDeleteProductShouldCorrect() throws Exception {
        // Mock method
        when(productRepository.findOne(product.getId())).thenReturn(product);
        doNothing().when(productRepository).delete(product);

        testResponseData(RequestInfo.builder()
            .request(delete(PRODUCT_DETAIL_ENDPOINT, product.getId()))
            .token(adminToken)
            .httpStatus(HttpStatus.OK)
            .build());
    }
}
