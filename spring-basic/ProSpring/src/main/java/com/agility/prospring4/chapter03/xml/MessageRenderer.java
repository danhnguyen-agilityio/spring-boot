package com.agility.prospring4.chapter03.xml;

public interface MessageRenderer {
  void render();
  void setMessageProvider(MessageProvider provider);
  MessageProvider getMessageProvider();
}
