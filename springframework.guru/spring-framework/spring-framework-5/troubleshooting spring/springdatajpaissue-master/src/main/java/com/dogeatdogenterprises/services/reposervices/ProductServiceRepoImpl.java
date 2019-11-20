package com.dogeatdogenterprises.services.reposervices;

import com.dogeatdogenterprises.commands.ProductForm;
import com.dogeatdogenterprises.converters.ProductFormToProduct;
import com.dogeatdogenterprises.converters.ProductToProductForm;
import com.dogeatdogenterprises.domain.Product;
import com.dogeatdogenterprises.repositories.ProductRepository;
import com.dogeatdogenterprises.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by donaldsmallidge on 3/7/17.
 */
@Service
@Profile("springdatajpa")
public class ProductServiceRepoImpl implements ProductService {

    private ProductRepository productRepository;
    private ProductFormToProduct productFormToProduct;
    private ProductToProductForm productToProductForm;

    @Autowired
    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Autowired
    public void setProductFormToProduct(ProductFormToProduct productFormToProduct) {
        this.productFormToProduct = productFormToProduct;
    }

    @Autowired
    public void setProductToProductForm(ProductToProductForm productToProductForm) {
        this.productToProductForm = productToProductForm;
    }

    @Override
    public List<?> listAll() {
        List<Product> products = new ArrayList<>();
        productRepository.findAll().forEach(products::add); // fun with Java 8
        return products;
    }

    @Override
    public Product getById(Integer id) {
        return productRepository.findOne(id);
    }

    @Override
    public Product saveOrUpdate(Product domainObject) {
        return productRepository.save(domainObject);
    }

//    @Override
//    public Product saveOrUpdateProductForm(ProductForm productForm) {
//        Product newProduct = productFormToProduct.convert(productForm);
//
//        return saveOrUpdateProductForm(newProduct);
//    }

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
        productRepository.delete(id);
    }


}
