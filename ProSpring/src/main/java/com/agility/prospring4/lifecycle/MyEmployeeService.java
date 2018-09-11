package com.agility.prospring4.lifecycle;

public class MyEmployeeService {
  private Employee employee;

  public Employee getEmployee() {
    return employee;
  }

  public void setEmployee(Employee employee) {
    this.employee = employee;
  }

  public MyEmployeeService() {
    System.out.println("MyEmployeeService no-args constructor called");
  }

  // pre-destroy method
  public void destroy() throws Exception {
    System.out.println("MyEmployeeService Closing resources");
  }

  // Post init method
  public void init() {
    System.out.println("MyEmployeeService initializing to dummy value");
  }
}
