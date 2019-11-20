package com.dogeatdogenterprises.converters;

import com.dogeatdogenterprises.commands.ProductForm;
import com.dogeatdogenterprises.domain.Product;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * Created by donaldsmallidge on 3/9/17.
 */
@Component
public class ProductToProductForm implements Converter<Product, ProductForm> {

    @Override
    public ProductForm convert(Product product) {

        ProductForm productForm = new ProductForm();
        productForm.setId(product.getId());
        productForm.setVersion(product.getVersion());
        productForm.setDescription(product.getDescription());
        productForm.setPrice(product.getPrice());
        productForm.setImageUrl(product.getImageUrl());

        return productForm;
    }
}
