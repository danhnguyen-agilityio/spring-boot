package appstart.mymodappdemo;

import appfuncs.simplefuncs.SimpleMathFuncs;
import appsupport.supportfuncs.SupportFuncs;

/** MyModeApp demo */
public class MyModAppDemo {
  public static void main(String args[]) {
    if (SupportFuncs.isFactor(2, 10)) {
      System.out.println("2 is a factor of 10");
    }
  }
}