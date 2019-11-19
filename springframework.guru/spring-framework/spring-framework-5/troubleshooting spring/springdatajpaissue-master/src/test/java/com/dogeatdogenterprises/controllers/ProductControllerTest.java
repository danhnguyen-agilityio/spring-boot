package com.dogeatdogenterprises.controllers;

import com.dogeatdogenterprises.commands.ProductForm;
import com.dogeatdogenterprises.controllers.ProductController;
import com.dogeatdogenterprises.converters.ProductToProductForm;
import com.dogeatdogenterprises.domain.Product;
import com.dogeatdogenterprises.services.ProductService;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

/**
 * Created by donaldsmallidge on 10/14/16.
 */
public class ProductControllerTest {

    //Mockito Mock object
    @Mock
    private ProductService productService;

    // sets up controller, and injects mock objects into it
    @InjectMocks
    private ProductController productController;
    private MockMvc mockMvc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this); // initializes controller and mocks

        //  productController.setProductFormToProduct(new ProductFormToProduct());
        productController.setProductToProductForm(new ProductToProductForm()); // <-- added 4/12/2017

        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
    }

    @Test
    public void testList() throws Exception {

        List<Product> products = new ArrayList<>();
        products.add(new Product());
        products.add(new Product());

        // specific Mockito interaction, tell stub to return list of products
        when(productService.listAll()).thenReturn((List)products);
        // Note: need to strip generics to keep Mockito happy.

        mockMvc.perform(get("/product/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("product/list"))
                .andExpect(model().attribute("products", hasSize(2)));
    }

    @Test
    public void testShow() throws Exception {

        Integer id = 1;

        // Mockito stub to return new product for ID 1
        when(productService.getById(id)).thenReturn(new Product());

        mockMvc.perform(get("/product/show/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("product/show"))
                .andExpect(model().attribute("product", instanceOf(Product.class)));
    }

    // copied from sfg
    @Test
    public void testEdit() throws Exception{
        Integer id = 1;

        // Tell Mockito stub to return new product for ID 1
        when(productService.getById(id)).thenReturn(new Product());

        mockMvc.perform(get("/product/edit/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("product/productform"))
                .andExpect(model().attribute("productForm", instanceOf(ProductForm.class))); // <-- revised 4/12/2017
    }

    @Test
    public void testNewProduct() throws Exception {

        Integer id = 1;

        // should not call service
        verifyZeroInteractions(productService);

        mockMvc.perform(get("/product/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("product/productform"))
                .andExpect(model().attribute("productForm", instanceOf(ProductForm.class)));
    }

    @Test
    public void testSaveOrUpdate() throws Exception {

        Integer id = 1;
        String description = "Test Description";
        BigDecimal price = new BigDecimal("12.00");
        String imageUrl = "http://example.com"; // <-- revised 4/12/2017 - added prefix http://

        Product returnProduct = new Product();
        returnProduct.setId(id);
        returnProduct.setDescription(description);
        returnProduct.setPrice(price);
        returnProduct.setImageUrl(imageUrl);
        ProductToProductForm productToProductForm = new ProductToProductForm(); // <-- revised 4/12/2017

        // Mockito stub to return new product for ID 1
        // Note: I had to specify org.mockito instead of Hamcrest. WHY?
        //when(productService.saveOrUpdate( org.mockito.Matchers.<Product>any())).thenReturn(returnProduct);
        // PROBLEM 4/12/2017: expecting ProductForm, not Product; should be Product????? but refactoring breaks everything!
        when(productService.saveOrUpdateProductForm(org.mockito.Matchers.<ProductForm>any())).thenReturn(productToProductForm.convert(returnProduct));
 //       when(productService.getById(org.mockito.Matchers.<Integer>any())).thenReturn(returnProduct);

        mockMvc.perform(post("/product")
                .param("id", "1")
                .param("description", description)
                .param("price", "12.00")
                .param("imageUrl", "http://example.com"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/product/show/1")); // <-- revisions 4/12/2017
//                .andExpect(model().attribute("product", instanceOf(Product.class)))
//                .andExpect(model().attribute("product", hasProperty("id", is(id))))
//                .andExpect(model().attribute("product", hasProperty("description", is(description))))
//                .andExpect(model().attribute("product", hasProperty("price", is(price))))
//                .andExpect(model().attribute("product", hasProperty("imageUrl", is(imageUrl))));

        // verify properties of bound object <-- revised 4/12/2017 - ProductForm, not Product
        ArgumentCaptor<ProductForm> boundProduct = ArgumentCaptor.forClass(ProductForm.class);
        verify(productService).saveOrUpdateProductForm(boundProduct.capture());

        assertEquals(id, boundProduct.getValue().getId());
        assertEquals(description, boundProduct.getValue().getDescription());
        assertEquals(price, boundProduct.getValue().getPrice());
        assertEquals(imageUrl, boundProduct.getValue().getImageUrl());
    }

    @Test
    public void testDelete() throws Exception {

        Integer id = 1;

        // Mockito stub to return new product for ID 1 - NOT IN INSTRUCTOR VERSION -  NOT NEEDED
//        when(productService.getById(id)).thenReturn(new Product());

        mockMvc.perform(get("/product/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/product/list"));

        verify(productService, times(1)).delete(id);
    }
}
