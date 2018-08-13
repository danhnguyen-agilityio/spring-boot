package behavior.visitor.pattern.demo1;

interface Human {
  void hit(Monster monster);
}

interface Monster {
  void hitBy(Warrior warrior);
  void hitBy(Wizard wizard);
}

class Warrior implements Human {
  public void hit(Monster monster) {
    System.out.println("Let me introduce you: my hammer!!!");
    monster.hitBy(this);
  }
}

class Wizard implements Human {
  public void hit(Monster monster) {
    System.out.println("Avada Kedavra");
    monster.hitBy(this);
  }
}

class CuteDogie implements Monster {

  public void damaged(int hp) {
    System.out.println("Woo I lost " + hp + " hp");
  }

  @Override
  public void hitBy(Warrior warrior) {
    damaged(50);
  }

  @Override
  public void hitBy(Wizard wizard) {
    damaged(10);
  }
}

class Dracula implements Monster {

  public void damaged(int hp) {
    System.out.println("Woo I lost " + hp + " hp");
  }

  @Override
  public void hitBy(Warrior warrior) {
    damaged(10);
  }

  @Override
  public void hitBy(Wizard wizard) {
    damaged(80);
  }
}

public class VisitorPatternEx {
  public static void main(String[] args) {
    Human warrior = new Warrior();
    Human wizard = new Wizard();

    Monster dogie = new CuteDogie();
    Monster dracula = new Dracula();

    warrior.hit(dogie);
    wizard.hit(dogie);

    warrior.hit(dracula);
    wizard.hit(dracula);
  }
}
