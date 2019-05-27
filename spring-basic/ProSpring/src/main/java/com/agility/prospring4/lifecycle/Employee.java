package com.agility.prospring4.lifecycle;

public class Employee {
  private String name;

  public Employee() {
    System.out.println("Employee");
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
