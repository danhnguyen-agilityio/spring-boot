package behavior.mediator.pattern.demo;

abstract class Friend {
  Mediator mediator;
  String name;

  Friend(Mediator mediator) {
    this.mediator = mediator;
  }
}

class Friend1 extends Friend {
  public Friend1(Mediator mediator, String name) {
    super(mediator);
    this.name = name;
  }

  public void send(String msg) {
    mediator.send(this, msg);
  }

  public void notify(String msg) {
    System.out.println("Amit gets message: " + msg);
  }
}


class Friend2 extends Friend {
  public Friend2(Mediator mediator, String name) {
    super(mediator);
    this.name = name;
  }

  public void send(String msg) {
    mediator.send(this, msg);
  }

  public void notify(String msg) {
    System.out.println("Sohel gets message: " + msg);
  }
}

class Boss extends Friend {
  public Boss(Mediator mediator, String name) {
    super(mediator);
    this.name = name;
  }

  public void send(String msg) {
    mediator.send(this, msg);
  }

  public void notify(String msg) {
    System.out.println("Boss sees message: " + msg);
    System.out.println("");
  }
}

abstract class Mediator {
  public abstract void send(Friend frd, String msg);
}

class ConcreteMedicator extends Mediator {
  private Friend1 friend1;
  private Friend2 friend2;
  private Boss boss;

  public void setFriend1(Friend1 friend1) {
    this.friend1 = friend1;
  }

  public void setFriend2(Friend2 friend2) {
    this.friend2 = friend2;
  }

  public void setBoss(Boss boss) {
    this.boss = boss;
  }

  @Override
  public void send(Friend frd, String msg) {
    if (frd == friend1) {
      friend2.notify(msg);
      boss.notify(friend1.name + " sends message to " + friend2.name);
    }
    if (frd == friend2) {
      friend1.notify(msg);
      boss.notify(friend2.name + " sends message to " + friend1.name);
    }
    if (frd == boss) {
      friend1.notify(msg);
      friend2.notify(msg);
    }
  }
}

public class MediatorPatternEx {
  public static void main(String[] args) {
    ConcreteMedicator concreteMedicator = new ConcreteMedicator();
    Friend1 Amit = new Friend1(concreteMedicator, "Amit");
    Friend2 Sohel = new Friend2(concreteMedicator, "Sohel");
    Boss Raghu = new Boss(concreteMedicator, "Raghu");

    concreteMedicator.setFriend1(Amit);
    concreteMedicator.setFriend2(Sohel);
    concreteMedicator.setBoss(Raghu);

    Amit.send("[Amit here] Good morning. Can we discuss the mediator pattern.");
    Sohel.send("[Sohel here]Yes, we can discuss now.");
    Raghu.send("[Raghu here] Please get back to work quickly");

  }
}
