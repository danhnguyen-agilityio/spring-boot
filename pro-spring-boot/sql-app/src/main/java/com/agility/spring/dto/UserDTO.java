package com.agility.spring.dto;

import javax.validation.constraints.Email;
import java.io.Serializable;

public class UserDTO implements Serializable {
  private long id;
  @Email
  private String email;
  private String lastName;

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
    return "Email: " + email + ", Name: " + lastName;
  }
}
