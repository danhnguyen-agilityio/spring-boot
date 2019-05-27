package com.agility.prospring4.chapter03.methodinjection;

public abstract class AbstractLookupDemoBean implements DemoBean {

  public abstract MyHelper getMyHelper();

  public void someOperation() {
    getMyHelper().doSomethingHelpful();
  }
}
