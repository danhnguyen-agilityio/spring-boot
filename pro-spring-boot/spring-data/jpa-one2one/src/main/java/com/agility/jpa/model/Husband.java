package com.agility.jpa.model;

import javax.persistence.*;

@Entity
@Table(name = "husband")
public class Husband {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;

  private String name;

  @OneToOne(mappedBy = "husband")
  private Wife wife;

  public Husband() {
  }

  public Husband(String name) {
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

  public Wife getWife() {
    return wife;
  }

  public void setWife(Wife wife) {
    this.wife = wife;
  }

  @Override
  public String toString() {
    return String.format("Husband: %s, Wife: %s", name, wife.getName());
  }
}
