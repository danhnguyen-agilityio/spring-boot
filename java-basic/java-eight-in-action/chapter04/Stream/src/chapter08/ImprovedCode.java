package chapter08;

public class ImprovedCode {

  interface Task {
    void execute();
  }

  public static void main(String[] args) {
    ImprovedCode improvedCode = new ImprovedCode();
    improvedCode.demo();
  }

  public void demo() {
    int a = 10;
    System.out.println(this);

    Runnable r1 = new Runnable() {
      @Override
      public void run() {
        int a = 2;
        System.out.println("Anonymous class: " + this); // class itself
        System.out.println("Hello");
      }
    };

    Runnable r2 = () -> {
//      int a = 10; // compile error
      System.out.println("Lambda expression: " + this); // enclosing class
      System.out.println("Hello");
    };

    r1.run();
    r2.run();

    doSomething(new Task() {
      @Override
      public void execute() {
        System.out.println("Danger danger");
      }
    });

    // Problem; both doSomething(Runnable) and doSomething(Task) match
//    doSomething(() -> System.out.println("Danger danger"));

    doSomething((Task)() -> System.out.println("Danger danger"));
  }

  public static void doSomething(Runnable r) {
    r.run();
  }

  public static void doSomething(Task a) {
    a.execute();
  }
}
