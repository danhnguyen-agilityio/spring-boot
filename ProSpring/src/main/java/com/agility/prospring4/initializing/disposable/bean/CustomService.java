package com.agility.prospring4.initializing.disposable.bean;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public class CustomService implements InitializingBean, DisposableBean {
  String message;

  public CustomService() {
    System.out.println("CustomerService constructor");
  }

  public String getMessage() {
    System.out.println("Get message");
    return message;
  }

  public void setMessage(String message) {
    System.out.println("Set message");
    this.message = message;
  }

  public void destroy() throws Exception {
    System.out.println("Spring Container is destroy! Customer clean up");
  }

  public void afterPropertiesSet() throws Exception {
    System.out.println("Init method after properties are set: " + message);
  }
}
