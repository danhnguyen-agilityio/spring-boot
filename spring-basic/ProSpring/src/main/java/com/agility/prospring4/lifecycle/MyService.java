package com.agility.prospring4.lifecycle;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class MyService {
  private Employee employee;

  public Employee getEmployee() {
    return employee;
  }

  public void setEmployee(Employee employee) {
    this.employee = employee;
  }

  @PostConstruct
  public void init() {
    System.out.println("MyService init method called");
  }

  public MyService() {
    System.out.println("MyService no-args constructor called");
  }

  @PreDestroy
  public void destroy() {
    System.out.println("MyService destroy method called");
  }
}
