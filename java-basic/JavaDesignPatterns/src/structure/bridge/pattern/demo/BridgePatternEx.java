package structure.bridge.pattern.demo;

/**
 * Implementor
 */
interface DrawingAPI {
  public void drawCircle(double x, double y, double radius);
}

/**
 * ConcreteImplementor
 */
class DrawingAPI1 implements DrawingAPI {
  public void drawCircle(double x, double y, double radius) {
    System.out.printf("API1 circle at %f:%f radius %f\n", x, y, radius);
  }
}

/**
 * ConcreteImplementor
 */
class DrawingAPI2 implements DrawingAPI {
  public void drawCircle(double x, double y, double radius) {
    System.out.printf("API2 circle at %f:%f radius %f\n", x, y, radius);
  }
}

/**
 * Abstraction
 */
interface Shape {
  public void draw();
  public void resize(double pct);
}

/**
 * Refined Abstraction
 */
class CircleShape implements Shape {
  private double x, y, radius;
  private DrawingAPI drawingAPI;

  public CircleShape(double x, double y, double radius, DrawingAPI drawingAPI) {
    this.x = x;
    this.y = y;
    this.radius = radius;
    this.drawingAPI = drawingAPI;
  }

  @Override
  public void draw() {
    drawingAPI.drawCircle(x, y, radius);
  }

  @Override
  public void resize(double pct) {
    radius *= pct;
  }
}

public class BridgePatternEx {
  public static void main(String[] args) {
    Shape[] shapes = new Shape[2];
    shapes[0] = new CircleShape(1, 2, 3, new DrawingAPI1());
    shapes[1] = new CircleShape(5, 7, 11, new DrawingAPI2());

    for (Shape shape : shapes) {
      shape.resize(2.5);
      shape.draw();
    }
  }
}
