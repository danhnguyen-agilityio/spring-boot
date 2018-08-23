package com.agility.spring.models;

import com.agility.spring.dto.UserDTO;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "users")
//@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class User implements Serializable {

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

  public User(UserDTO userDTO) {
    this(userDTO.getEmail(), userDTO.getLastName());
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
    return "Id: " + this.id + ", " + "Name: " + this.lastName + ", " + "Email: " + this.email;
  }
}
