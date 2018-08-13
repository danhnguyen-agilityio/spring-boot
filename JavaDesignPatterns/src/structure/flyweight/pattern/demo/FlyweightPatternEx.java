package structure.flyweight.pattern.demo;

import structure.facade.pattern.demo.robotfacade.RobotFacade;

import java.util.HashMap;
import java.util.Map;

interface IRobot {
  void print();
}

class SmallRobot implements IRobot {
  @Override
  public void print() {
    System.out.println("This is a Small Robot");
  }
}

class LargeRobot implements IRobot {
  @Override
  public void print() {
    System.out.println("This is a Large Robot");
  }
}

class RobotFactory {
  Map<String, IRobot> shapes = new HashMap<>();

  public int totalObjectsCreated() {
    return shapes.size();
  }

  public IRobot getRobotFromFactory(String robotFactory) throws Exception {
    IRobot robot = null;
    if (shapes.containsKey(robotFactory)) {
      robot = shapes.get(robotFactory);
    } else {
      switch (robotFactory) {
        case "small":
          System.out.println("We do not have Small Robot");
          robot = new SmallRobot();
          shapes.put("small", robot);
          break;
        case "large":
          System.out.println("We do not have Large Robot");
          robot = new LargeRobot();
          shapes.put("large", robot);
          break;
        default:
          throw new Exception("Robot factory can create only small and large shapes");

      }
    }
    return robot;
  }
}

public class FlyweightPatternEx {
  public static void main(String[] args) throws Exception {
    RobotFactory myFactory = new RobotFactory();
    IRobot shape = myFactory.getRobotFromFactory("small");
    shape.print();

    for (int i = 0; i < 2; i++) {
      shape = myFactory.getRobotFromFactory("small");
      shape.print();
    }
    System.out.println("Distinct Robot objects created till now = " + myFactory.totalObjectsCreated());


    for (int i = 0; i < 5; i++) {
      shape = myFactory.getRobotFromFactory("large");
      shape.print();
    }
    System.out.println("Distinct Robot objects created till now = " + myFactory.totalObjectsCreated());

  }
}
