package com.agility.prospring4.chapter03.annotation;

import org.springframework.stereotype.Service;

@Service("messageRenderer")
public class StandardOutMessageRenderer implements MessageRenderer {
  private MessageProvider messageProvider;

  public void render() {
    if (messageProvider == null) {
      throw new RuntimeException("You must set the property messageProvider of " +
          "class: " + StandardOutMessageRenderer.class.getName());
    }

    System.out.println(messageProvider.getMessage());
  }

  public void setMessageProvider(MessageProvider provider) {
    this.messageProvider = provider;
  }

  public MessageProvider getMessageProvider() {
    return this.messageProvider;
  }
}
