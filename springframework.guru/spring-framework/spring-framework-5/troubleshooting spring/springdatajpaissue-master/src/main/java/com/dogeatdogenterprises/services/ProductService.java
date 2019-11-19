package com.dogeatdogenterprises.services;

import com.dogeatdogenterprises.commands.ProductForm;
import com.dogeatdogenterprises.domain.Product;

/**
 * Created by donaldsmallidge on 9/14/16.
 */
public interface ProductService extends CRUDService<Product> {

    // Product saveOrUpdateProductForm(ProductForm productForm);
    ProductForm saveOrUpdateProductForm(ProductForm productForm); // revised 4/12/2017
}

