package com.agility.prospring4.chapter03.annotation;

import org.springframework.stereotype.Service;

@Service("messageProvider")
public class HelloWorldMessageProvider implements MessageProvider {

  public String getMessage() {
    return "Hello World";
  }
}
