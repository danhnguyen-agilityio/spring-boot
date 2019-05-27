package behavior.visitor.pattern.demo;

interface Animal {
  void accept(Visitor v);
}

class Dog implements Animal {
  public void gogo() {
    System.out.println("Gogo");
  }

  public void hehe() {

  }

  @Override
  public void accept(Visitor v) {
    v.visit(this);
  }
}

class Cat implements Animal {
  public void meomeo() {
    System.out.println("Meomeo");
  }

  public void hihi() {

  }

  @Override
  public void accept(Visitor v) {
    v.visit(this);
  }
}

interface Visitor {
  void visit(Dog dog);
  void visit(Cat cat);
}

class SayVisitor implements Visitor {

  @Override
  public void visit(Dog dog) {
    dog.gogo();
  }

  @Override
  public void visit(Cat cat) {
    cat.meomeo();
  }
}

class SmileVisitor implements Visitor {

  @Override
  public void visit(Dog dog) {
    dog.hehe();
  }

  @Override
  public void visit(Cat cat) {
    cat.hihi();
  }
}

public class VisitorPatternEx {
  void somewhere() {
    Animal a = new Dog();
    if (a instanceof Dog) {
      ((Dog) a).gogo();
    } else if (a instanceof Cat) {
      ((Cat) a).meomeo();
    }
  }

  public static void main(String[] args) {
    Animal a = new Dog();
    a.accept(new SayVisitor());
  }
}
