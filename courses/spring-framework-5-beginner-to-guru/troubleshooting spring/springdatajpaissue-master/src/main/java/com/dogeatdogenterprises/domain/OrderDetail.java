package com.dogeatdogenterprises.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

/**
 * Created by donaldsmallidge on 2/19/17.
 */
@Entity
public class OrderDetail extends AbstractDomainClass {

    @ManyToOne
    private Order order;

    @OneToOne
    private Product product;

    private Integer quantity; // added 17 Feb 2017

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }


}
