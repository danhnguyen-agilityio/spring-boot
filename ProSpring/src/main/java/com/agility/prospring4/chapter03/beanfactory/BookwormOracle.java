package com.agility.prospring4.chapter03.beanfactory;

import org.springframework.stereotype.Service;

@Service("oracle")
public class BookwormOracle implements Oracle {
  public String defineMeaningOfLife() {
    return "Waste of time";
  }
}
