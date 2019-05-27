package com.agility.prospring4.chapter03.xml;

public class ConfigurableMessageProvider implements MessageProvider {
  private String message;
  private String author;

  public ConfigurableMessageProvider(String message, String author) {
    this.message = message;
    this.author = author;
  }

  public String getMessage() {
    return message + " " + author;
  }
}
