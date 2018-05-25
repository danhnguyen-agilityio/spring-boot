// 364 376
/** Class for two-dimensinal objects */
class TwoDShape {
  private double width;
  private double height;

  /** A default constructor */
  TwoDShape() {
    System.out.println("Construct TwoDShape");
    width = 0;
    height = 0;
  }

  /** Constructor params */
  TwoDShape(double w, double h) {
    System.out.println("Construct TwoDShape");
    width = w;
    height = h;
  }

  /** Constructor object with equal width and height */
  TwoDShape(double x) {
    System.out.println("Construct TwoDShape");
    width = x;
    height = x;
  }

  /** Contructor an objec from an object */
  TwoDShape(TwoDShape ob) {
    width = ob.width;
    height = ob.height;
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
    System.out.println("Construct Triangle");
    style = "none";
  }

  Triangle(String s, double w, double h) {
    super(w, h);
    System.out.println("Construct Triangle");
    style = s;
  }

  /** One arg constructor */
  Triangle(double x) {
    super(x);
    System.out.println("Construct Triangle");
    style = "filled";
  }

  /** Contructor an object from an object */
  Triangle(Triangle ob) {
    super(ob);
    style = ob.style;
  }

  /** Get area */
  double area() {
    return getWidth() * getHeight() / 2;
  }

  void showStyle() {
    System.out.println("Triangle: " + style);
  }
}

/** Extend Triangle */
class ColorTriangle extends Triangle {
  private String color;

  ColorTriangle(String c, String s, double w, double h) {
    super(s, w, h);
    System.out.println("Construct ColorTriangle");
    color = c;
  }

  String getColor() {
    return color;
  }

  void showColor() {
    System.out.println("color is: " + color);
  }
}

class Shape {
  public static void main(String args[]) {
    Triangle t1 = new Triangle();
    Triangle t2 = new Triangle("outlined", 8.0, 12.0);
    Triangle t3 = new Triangle(4.0);
    ColorTriangle t4 = new ColorTriangle("Blue", "outlined", 8.0, 12.0);
    Triangle t5 = new Triangle(t2);

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

    System.out.println("Info for t4: ");
    t4.showStyle();
    t4.showDim();
    t4.showColor();
    System.out.println("Area is " + t4.area());

    System.out.println("Info for t5: ");
    t5.showStyle();
    t5.showDim();
    System.out.println("Area is " + t5.area());
  }
}