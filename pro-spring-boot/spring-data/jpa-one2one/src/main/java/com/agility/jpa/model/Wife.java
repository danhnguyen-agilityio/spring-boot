package com.agility.jpa.model;

import javax.persistence.*;

@Entity
@Table(name = "wife")
public class Wife {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;

  private String name;

  @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JoinColumn(name = "husband_id")
  private Husband husband;

  public Wife() {}

  public Wife(String name) {
    this.name = name;
  }

  public Wife(String name, Husband husband) {
    this.name = name;
    this.husband = husband;
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

  public Husband getHusband() {
    return husband;
  }

  public void setHusband(Husband husband) {
    this.husband = husband;
  }

  @Override
  public String toString() {
    return String.format("Wife: %s, Husband: %s", name, husband.getName());
  }
}
