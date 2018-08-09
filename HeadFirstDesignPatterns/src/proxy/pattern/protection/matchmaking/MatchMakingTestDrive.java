package proxy.pattern.protection.matchmaking;

import java.lang.reflect.Proxy;

public class MatchMakingTestDrive {
  /**
   * Create own Proxy class and instantiating the own Proxy object
   */
  PersonBean getOwnProxy(PersonBean person) {
    return (PersonBean) Proxy.newProxyInstance(
        person.getClass().getClassLoader(),
        person.getClass().getInterfaces(),
        new OwnerInvocationHandler(person)
    );
  }

  /**
   * Create non own Proxy class and instantiating the not own Proxy object
   */
  PersonBean getNonOwnProxy(PersonBean person) {
    return (PersonBean) Proxy.newProxyInstance(
        person.getClass().getClassLoader(),
        person.getClass().getInterfaces(),
        new NonOwnerInvocationHandler(person)
    );
  }

  public static void main(String[] args) {
    MatchMakingTestDrive testDrive = new MatchMakingTestDrive();
    testDrive.drive();
  }

  public void drive() {
    PersonBean joe = new PersonBeanImpl("Joe", "Male", "Music",
        7, 1);

    // If customer is joe
    PersonBean ownerProxy = getOwnProxy(joe);
    System.out.println("This is proxy class: " + Proxy.isProxyClass(ownerProxy.getClass()));
    System.out.println("Name :" + ownerProxy.getName());
    try {
      ownerProxy.setHotOrNotRating(10);
    } catch (Exception e) {
      System.out.println("Can set rating from owner proxy");
    }

    // If customer not equal joe
    PersonBean nonOwnerProxy = getNonOwnProxy(joe);
    System.out.println("Name :" + nonOwnerProxy.getName());
    nonOwnerProxy.setHotOrNotRating(10);
    try {
      nonOwnerProxy.setName("David");
    } catch (Exception e) {
      System.out.println("Can set name from non owner proxy");
    }

  }
}
