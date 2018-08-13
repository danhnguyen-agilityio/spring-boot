package structure.flyweight.pattern.demo2;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

interface IRobot {
  void print();
}

class Robot implements IRobot {
  String robotType;
  public String colorOfRobot;

  public Robot(String robotType) {
    this.robotType = robotType;
  }

  public void setColor(String colorOfRobot) {
    this.colorOfRobot = colorOfRobot;
  }

  @Override
  public void print() {
    System.out.println("This is a " + robotType + " type robot with " + colorOfRobot + " color");
  }
}

class RobotFactory {
  Map<String, IRobot> shapes = new HashMap<>();

  public int totalObjectsCreated() {
    return shapes.size();
  }

  public IRobot getRobotFromFactory(String robotType) throws Exception {
    IRobot robot = null;
    if (shapes.containsKey(robotType)) {
      robot = shapes.get(robotType);
    } else {
      switch (robotType) {
        case "King":
          System.out.println("We do not have King Robot");
          robot = new Robot("King");
          shapes.put("King", robot);
          break;
        case "Queen":
          System.out.println("We do not have Queen Robot");
          robot = new Robot("Queen");
          shapes.put("Queen", robot);
          break;
        default:
          throw new Exception("Robot factory can create only king and queen type robots");
      }
    }

    return robot;
  }
}

public class FlyweightPatternEx {
  public static void main(String[] args) throws Exception {
    RobotFactory robotFactory = new RobotFactory();
    Robot shape;
    for (int i = 0; i < 3; i++) {
      shape = (Robot) robotFactory.getRobotFromFactory("King");
      shape.setColor(getRandomColor());
      shape.print();
    }

    for (int i = 0; i < 3; i++) {
      shape = (Robot) robotFactory.getRobotFromFactory("Queen");
      shape.setColor(getRandomColor());
      shape.print();
    }

    System.out.println("Finally no of Distinct Robot objects created: " + robotFactory.totalObjectsCreated());
  }


  private static String getRandomColor() {
    Random r = new Random();
    int random = r.nextInt(20);
    if (random % 2 == 0) {
      return "red";
    } else {
      return "green";
    }

  }
}
