package structure.bridge.pattern.demo1;

interface IColor {
  void fillWithColor(int border);
}

class RedColor implements IColor {

  @Override
  public void fillWithColor(int border) {
    System.out.println("Red color with " + border + " inch border");
  }
}

class GreenColor implements IColor {

  @Override
  public void fillWithColor(int border) {
    System.out.println("Green color with " + border + " inch border");
  }
}

abstract class Shape {
  protected IColor color;
  protected Shape(IColor c) {
    this.color = c;
  }

  abstract void drawShape(int border);
  abstract void modifyBorder(int border, int increment);
}

class Triangle extends Shape {

  protected Triangle(IColor c) {
    super(c);
  }

  @Override
  void drawShape(int border) {
    System.out.println("This Triangle is colored with: ");
    color.fillWithColor(border);
  }

  @Override
  void modifyBorder(int border, int increment) {
    System.out.println("Now we are changing the border length " + increment + " times" );
    border = border * increment;
    drawShape(border);
  }
}

class Rectangle extends Shape {

  protected Rectangle(IColor c) {
    super(c);
  }

  @Override
  void drawShape(int border) {
    System.out.println("This Rectangle is colored with: ");
    color.fillWithColor(border);
  }

  @Override
  void modifyBorder(int border, int increment) {
    System.out.println("Now we are changing the border length " + increment + " times" );
    border = border * increment;
    drawShape(border);
  }
}

public class BridgePatternEx {
  public static void main(String[] args) {
    System.out.println("Coloring Triangle:");
    IColor green = new GreenColor();
    Shape triangleShape = new Triangle(green);
    triangleShape.drawShape(20);;
    triangleShape.modifyBorder(20, 3);

    // Coloring Red to Rectangle
    System.out.println("Coloring Rectangle: ");
    IColor red = new RedColor();
    Shape rectangleShape = new Rectangle(red);
    rectangleShape.drawShape(50);
    rectangleShape.modifyBorder(50, 2);
  }
}
