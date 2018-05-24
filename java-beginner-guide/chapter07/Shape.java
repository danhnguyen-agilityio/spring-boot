// 336 342 346 356 364 376
/** Class for two-dimensinal objects */
class TwoDShape {
  private double width;
  private double height;

  /** Accessor methods for width and height */
  double getWidth() {
    return width;
  }
  double getHeight() {
    return height;
  }
  void setWidth(double w) {
    width = w;
  }
  void setHeight(double h) {
    height = h;
  }

  /** Show dimensional  */
  void showDim() {
    System.out.println("Wid and height: " + width + " and " + height);
  }
}

/** A subclass of TwoDShape for triangles */
class Triangle extends TwoDShape {
  String style;

  /** Get area */
  double area() {
    return getWidth() * getHeight() / 2;
  }

  void showStyle() {
    System.out.println("Triangle: " + style);
  }
}

class Shape {
  public static void main(String args[]) {
    Triangle t1 = new Triangle();
    Triangle t2 = new Triangle();

    t1.setWidth(4.0);
    t1.setHeight(4.0);
    t1.style = "filled";

    System.out.println("Info for t1: ");
    t1.showStyle();
    t1.showDim();
    System.out.println("Area is " + t1.area());
  }
}