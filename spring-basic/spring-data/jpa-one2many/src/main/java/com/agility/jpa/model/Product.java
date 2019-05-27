package com.agility.jpa.model;

import org.json.JSONObject;

import javax.persistence.*;

@Entity
@Table(name = "product")
public class Product {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;

  private String name;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "company_id")
  private Company company;

  public Product() {
  }

  public Product(String name) {
    this.name = name;
  }

  public Product(String name, Company company) {
    this.name = name;
    this.company = company;
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

  public Company getCompany() {
    return company;
  }

  public void setCompany(Company company) {
    this.company = company;
  }

  @Override
  public String toString() {
    String info;

    JSONObject jsonInfo = new JSONObject();
    jsonInfo.put("name", name);

    JSONObject companyObj = new JSONObject();
    companyObj.put("name", company.getName());
    jsonInfo.put("company", companyObj);

    info = jsonInfo.toString();
    return info;
  }
}
