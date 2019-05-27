package patterns.singleton.cracking;

import java.lang.reflect.Constructor;

public class CrackingSingleton {
  public static void main(String[] args) throws Exception {
    //First statement retrieves the Constructor object for private constructor of SimpleSingleton class.
    // singleton.patterns.cracking.SingletonObject
    // SingletonObject.class
    Constructor pvtConstructor = Class.forName("patterns.singleton.cracking.SingletonObject").getDeclaredConstructors()[0];

    //Since the constructor retrieved is a private one, we need to set its accessibility to true.
    pvtConstructor.setAccessible(true);

    //Last statement invokes the private constructor and create a new instance of SimpleSingleton class.
    SingletonObject notSingleton1 = (SingletonObject) pvtConstructor.newInstance(null);
    System.out.println(notSingleton1.hashCode());
    System.out.println("notSingleton1 --->" + notSingleton1.toString());

    SingletonObject  notSingleton2 = ( SingletonObject) pvtConstructor.newInstance(null);
    System.out.println("notSingleton2 --->"+notSingleton2.hashCode());
    System.out.println(notSingleton2.toString());


  }
}
