package com.agility.prospring4.chapter03.methodinjection;

public class ReplacementTarget {
  public String formatMessage(String msg) {
    return "<h1>" + msg + "</h1>";
  }

  public String formatMessage(Object msg) {
    return "<h1>" + msg + "</h1>";
  }
}
