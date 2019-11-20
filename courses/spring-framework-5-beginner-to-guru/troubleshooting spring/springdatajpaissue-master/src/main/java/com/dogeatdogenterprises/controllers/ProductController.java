package com.dogeatdogenterprises.controllers;

import com.dogeatdogenterprises.commands.ProductForm;
import com.dogeatdogenterprises.converters.ProductFormToProduct;
import com.dogeatdogenterprises.converters.ProductToProductForm;
import com.dogeatdogenterprises.domain.Product;
import com.dogeatdogenterprises.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

/**
 * Created by donaldsmallidge on 9/14/16.
 */
@Controller
// removed: @RequestMapping("/product")
public class ProductController {

    private ProductService productService;
    private ProductToProductForm productToProductForm;
    private ProductFormToProduct productFormToProduct; // <-- revised 4/12/2017

    @Autowired
    public void setProductService(ProductService productService) {

        this.productService = productService;
    }

    @Autowired
    public void setProductToProductForm(ProductToProductForm productToProductForm) {

        this.productToProductForm = productToProductForm;
    }

    @Autowired
    public void setProductFormToProduct(ProductFormToProduct productFormToProduct) {

        this.productFormToProduct = productFormToProduct;
    }

    @RequestMapping("/product/list")
    // removed: @RequestMapping({"/list", "/"})
    public String listProducts(Model model) {

        model.addAttribute("products", productService.listAll());

        return "product/list";
    }

    @RequestMapping("/product/show/{id}")
    // removed: @RequestMapping("/show/{id}")
    public String getProduct(@PathVariable Integer id, Model model) {

        model.addAttribute("product", productService.getById(id));

        return "product/show";
    }

    @RequestMapping("product/edit/{id}")
    // removed: @RequestMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        Product product = productService.getById(id);
        ProductForm productForm = productToProductForm.convert(product);

        model.addAttribute("productForm", productForm);

        return "product/productform";
    }

    @RequestMapping("/product/new")
    // removed: @RequestMapping("/new")
    public String newProduct(Model model) {
        model.addAttribute("productForm", new ProductForm());
        return "product/productform";
    }

    @RequestMapping(value = "/product", method = RequestMethod.POST)
    // removed: @RequestMapping(value = "/", method = RequestMethod.POST)
    public String saveOrUpdateProduct(@Valid ProductForm productForm, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "product/productform";
        }
        //ProductForm savedForm = productService.saveOrUpdateProductForm(productForm); // <-- revised 4/12/2017
        //Product savedProduct = productFormToProduct.convert(savedForm); // <--FAILING 4/13/2017
        ProductForm savedProduct = productService.saveOrUpdateProductForm(productForm); // instructor version
        return "redirect:/product/show/" + savedProduct.getId();
    }

    @RequestMapping("/product/delete/{id}")
    // removed: @RequestMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {

        productService.delete(id);
        return "redirect:/product/list";
    }
}
