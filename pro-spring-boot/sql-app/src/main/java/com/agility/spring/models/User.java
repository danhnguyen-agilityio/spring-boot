package com.agility.spring.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity
@Table(name = "users")
//@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  @JsonProperty("customEmail")
  private String email;

  @Column(name = "lastName")
  private String lastName;

  public User() {
  }

  public User(long id) {
    this.id = id;
  }

  public User(String email, String name) {
    this.email = email;
    this.lastName = name;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  @Override
  public String toString() {
    return "Id: " + id + ", " + "Name: " + lastName + ", " + "Email: " + email;
  }
}
