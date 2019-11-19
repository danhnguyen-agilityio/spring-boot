package com.dogeatdogenterprises.services.mapservices;

import com.dogeatdogenterprises.commands.ProductForm;
import com.dogeatdogenterprises.converters.ProductFormToProduct;
import com.dogeatdogenterprises.converters.ProductToProductForm;
import com.dogeatdogenterprises.domain.DomainObject;
import com.dogeatdogenterprises.domain.Product;
import com.dogeatdogenterprises.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by donaldsmallidge on 9/14/16.
 */
@Service
@Profile("map")
public class ProductServiceImpl extends AbstractMapService implements ProductService {

    private ProductFormToProduct productFormToProduct;
    private ProductToProductForm productToProductForm;

    @Autowired
    public void setProductFormToProduct(ProductFormToProduct productFormToProduct) {
        this.productFormToProduct = productFormToProduct;
    }

    @Autowired
    public void setProductToProductForm(ProductToProductForm productToProductForm) {
        this.productToProductForm = productToProductForm;
    }

    @Override
    public List<DomainObject> listAll() {

        return super.listAll();
    }

    @Override
    public Product getById(Integer id) {

        return (Product) super.getById(id);
    }

    @Override
    public Product saveOrUpdate(Product domainObject) {

        return (Product) super.saveOrUpdate(domainObject);
    }

//    @Override
//    public Product saveOrUpdateProductForm(ProductForm productForm) {
//        Product newProduct = productFormToProduct.convert(productForm);
//
//        return saveOrUpdateProductForm(newProduct);
//    }
//
    @Override
    public ProductForm saveOrUpdateProductForm(ProductForm productForm) {
        // instructor version
        if (productForm.getId() != null) { // existing product
            Product productToUpdate = this.getById(productForm.getId());

            productToUpdate.setVersion(productForm.getVersion());
            productToUpdate.setDescription(productForm.getDescription());
            productToUpdate.setPrice(productForm.getPrice());
            productToUpdate.setImageUrl(productForm.getImageUrl());

            return productToProductForm.convert(this.saveOrUpdate(productToUpdate));
        } else { // new product
            return productToProductForm.convert(this.saveOrUpdate(productFormToProduct.convert(productForm)));
        }
    }

    @Override
    public void delete(Integer id) {

        super.delete(id);
    }

/* SUPERSEDED: see SpringJPABootstrap
    @Override
    protected void loadDomainObjects() {

        domainMap = new HashMap<>();

        Product product1 = new Product();
        product1.setId(1);
        product1.setDescription("Product 1");
        product1.setPrice(new BigDecimal("12.99"));
        product1.setImageUrl("http://example.com/product1");

        domainMap.put(1, product1);

        Product product2 = new Product();
        product2.setId(2);
        product2.setDescription("Product 2");
        product2.setPrice(new BigDecimal("14.99"));
        product2.setImageUrl("http://example.com/product2");

        domainMap.put(2, product2);

        Product product3 = new Product();
        product3.setId(3);
        product3.setDescription("Product 3");
        product3.setPrice(new BigDecimal("34.99"));
        product3.setImageUrl("http://example.com/product3");

        domainMap.put(3, product3);

        Product product4 = new Product();
        product4.setId(4);
        product4.setDescription("Product 4");
        product4.setPrice(new BigDecimal("44.99"));
        product4.setImageUrl("http://example.com/product4");

        domainMap.put(4, product4);

        Product product5 = new Product();
        product5.setId(5);
        product5.setDescription("Product 5");
        product5.setPrice(new BigDecimal("25.99"));
        product5.setImageUrl("http://example.com/product5");

        domainMap.put(5, product5);

    }
    */
}
