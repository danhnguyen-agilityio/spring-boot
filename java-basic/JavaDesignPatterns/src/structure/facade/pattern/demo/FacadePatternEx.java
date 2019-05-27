package structure.facade.pattern.demo;

import structure.facade.pattern.demo.robotfacade.RobotFacade;

public class FacadePatternEx {
  public static void main(String[] args) {
    RobotFacade rf1 = new RobotFacade();
    rf1.constructRobot("Green", "Iron");

    RobotFacade rf2 = new RobotFacade();
    rf2.constructRobot("Blue", "Steel");
  }
}
