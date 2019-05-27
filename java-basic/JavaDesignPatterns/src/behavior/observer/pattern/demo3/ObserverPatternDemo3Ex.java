package behavior.observer.pattern.demo3;

public class ObserverPatternDemo3Ex {
  public static void main(String[] args) {
    System.out.println("Observer pattern demo3");
    Subject1 sub1 = new Subject1();
    Subject2 sub2 = new Subject2();

    Observer1 ob1 = new Observer1();
    Observer2 ob2 = new Observer2();
    Observer3 ob3 = new Observer3();

    sub1.register(ob1);
    sub1.register(ob2);

    sub2.register(ob2);
    sub2.register(ob3);

    sub1.setMyValue(50); // ob1 and ob2 notify
    sub2.setMyValue(250); // ob2 and  ob3 notify
  }
}
