// 346 356 364 376
/** Class for two-dimensinal objects */
class TwoDShape {
  private double width;
  private double height;

  /** A default constructor */
  TwoDShape() {
    width = 0;
    height = 0;
  }

  /** Constructor params */
  TwoDShape(double w, double h) {
    width = w;
    height = h;
  }

  /** Constructor object with equal width and height */
  TwoDShape(double x) {
    width = x;
    height = x;
  }

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
  private String style;

  /** A default constructor */
  Triangle() {
    super();
    style = "none";
  }

  Triangle(String s, double w, double h) {
    super(w, h);
    style = s;
  }

  /** One arg constructor */
  Triangle(double x) {
    super(x);
    style = "filled";
  }

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
    Triangle t2 = new Triangle("outlined", 8.0, 12.0);
    Triangle t3 = new Triangle(4.0);

    t1 = t2;

    System.out.println("Info for t1: ");
    t1.showStyle();
    t1.showDim();
    System.out.println("Area is " + t1.area());

    System.out.println("Info for t2: ");
    t2.showStyle();
    t2.showDim();
    System.out.println("Area is " + t2.area());

    System.out.println("Info for t3: ");
    t3.showStyle();
    t3.showDim();
    System.out.println("Area is " + t3.area());
  }
}