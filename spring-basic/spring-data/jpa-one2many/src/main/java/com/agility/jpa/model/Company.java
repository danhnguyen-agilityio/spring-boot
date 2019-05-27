package com.agility.jpa.model;

import com.fasterxml.jackson.databind.util.JSONPObject;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="company")
public class Company {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;

  private String name;

  @OneToMany(mappedBy = "company",cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  private Set<Product> products;

  public Company() {
  }

  public Company(String name) {
    this.name = name;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Set<Product> getProducts() {
    return products;
  }

  public void setProducts(Set<Product> products) {
    this.products = products;
  }

  @Override
  public String toString() {
    String info;
    JSONObject jsonInfo = new JSONObject();
    jsonInfo.put("name", name);

    JSONArray productArray = new JSONArray();
    if (products != null) {
      products.forEach(product -> {
        JSONObject subJson = new JSONObject();
        subJson.put("name", product.getName());
        productArray.put(subJson);
      });
    }
    jsonInfo.put("products", productArray);
    info = jsonInfo.toString();
    return info;
  }
}
