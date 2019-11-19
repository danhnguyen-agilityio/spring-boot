package com.dogeatdogenterprises.services;

import com.dogeatdogenterprises.config.JpaIntegrationConfig;
import com.dogeatdogenterprises.domain.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
//import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by donaldsmallidge on 12/6/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(JpaIntegrationConfig.class)
// https://spring.io/blog/2016/04/15/testing-improvements-in-spring-boot-1-4
//@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.DEFINED_PORT)
// not webEnvironment=WebEnvironment.RANDOM_PORT; DEFINED_PORT seems to default to 8080[?])
// http://docs.spring.io/spring-boot/docs/current/api/org/springframework/boot/test/context/SpringBootTest.WebEnvironment.html
@ActiveProfiles("jpadao")
public class ProductServiceJpaDaoImplTest {

    private ProductService productService;

    @Autowired
    public void setProductService(ProductService productService) {

        this.productService = productService;
    }

    @Test
    public void testListMethod() throws Exception {
        List<Product> products = (List<Product>)productService.listAll();
        assert products.size() == 5;
    }

    @Test
    public void testGetById() throws Exception {
        Integer id = 1;

        Product retrievedProduct = productService.getById(id);
        assert retrievedProduct.getDescription().equalsIgnoreCase("Product 1");
    }

    @Test
    public void testSaveOrUpdate() throws Exception {
        Integer id = 1;
        BigDecimal price = new BigDecimal("82.00");
        Product retrievedProduct = productService.getById(id);
        retrievedProduct.setPrice(price);

        Product modifiedProduct = productService.saveOrUpdate(retrievedProduct);

        assertEquals(id, modifiedProduct.getId());
        assertEquals(price, modifiedProduct.getPrice());
    }

    @Test
    public void testDelete() throws Exception {
        Integer id = 5;

        productService.delete(id);

        List<Product> products = (List<Product>)productService.listAll();
        assert products.size() == 4;
    }
}
