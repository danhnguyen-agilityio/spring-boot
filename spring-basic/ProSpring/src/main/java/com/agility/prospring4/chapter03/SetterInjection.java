package com.agility.prospring4.chapter03;

public class SetterInjection {
  private Dependency dependency;

  public void setDependency(Dependency dependency) {
    this.dependency = dependency;
  }

  @Override
  public String toString() {
    return dependency.toString();
  }
}
