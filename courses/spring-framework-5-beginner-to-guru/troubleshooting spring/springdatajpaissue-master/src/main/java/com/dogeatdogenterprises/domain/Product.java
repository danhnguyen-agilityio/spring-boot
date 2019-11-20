package com.dogeatdogenterprises.domain;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Created by donaldsmallidge on 9/14/16.
 */
@Entity
public class Product extends AbstractDomainClass {

    private String description;
    private BigDecimal price;
    private String imageUrl;

    public String getDescription() {

        return description;
    }

    public void setDescription(String description) {

        this.description = description;
    }

    public BigDecimal getPrice() {

        return price;
    }

    public void setPrice(BigDecimal price) {

        this.price = price;
    }

    public String getImageUrl() {

        return imageUrl;
    }


    public void setImageUrl(String imageUrl) {

        this.imageUrl = imageUrl;
    }
}
