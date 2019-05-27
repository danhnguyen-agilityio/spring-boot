package structure.facade.pattern.demo.robotfacade;

import structure.facade.pattern.demo.robotparts.RobotBody;
import structure.facade.pattern.demo.robotparts.RobotColor;
import structure.facade.pattern.demo.robotparts.RobotMetal;

public class RobotFacade {
  RobotColor robotColor;
  RobotMetal robotMetal;
  RobotBody robotBody;

  public RobotFacade() {
    robotColor = new RobotColor();
    robotMetal = new RobotMetal();
    robotBody = new RobotBody();
  }

  public void constructRobot(String color, String metal) {
    robotColor.setColor(color);
    robotMetal.setMetal(metal);
    robotBody.CreateBody();
  }
}
