package structure.facade.pattern.demo.robotparts;

public class RobotMetal {
  String metal;

  public void setMetal(String metal) {
    this.metal = metal;
    System.out.println("Metal is set to: " + this.metal);
  }
}
