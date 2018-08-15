package com.agility.prospring4.chapter03.annotation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service("messageProvider")
public class HelloWorldMessageProvider implements MessageProvider {
  private String message;

  @Autowired
//  public HelloWorldMessageProvider(@Value("Annotation configuration") String message) {
  public HelloWorldMessageProvider(String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }
}
