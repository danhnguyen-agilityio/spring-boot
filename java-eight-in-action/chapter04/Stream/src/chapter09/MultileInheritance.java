package chapter09;

interface Rotetable {
  void setRotationAngle(int angleInDegreees);
  int getRotationAngle();
  default void rotateBy(int angleInDegrees) {
    setRotationAngle((getRotationAngle() + angleInDegrees) % 360);
  }

  default void methodDefault() {
    System.out.println("Other method default");
  }
}

interface Moveable {
  int getX();
  int getY();
  void setX(int x);
  void setY(int y);

  default void moveHorizontally(int distance) {
    setX(getX() + distance);
  }

  default void moveVertically(int distance) {
    setY(getY() + distance);
  }
}

interface Resizable {
  int getWidth();
  int getHeight();
  void setWidth(int width);
  void setHeight(int height);
  void setAbsoluteSize(int width, int height);

  default void setRelativeSize(int wFactor, int hFactor) {
    setAbsoluteSize(getWidth() / wFactor, getHeight() / hFactor);
  }
}

public class MultileInheritance implements Rotetable, Moveable, Resizable {
  @Override
  public void setRotationAngle(int angleInDegreees) {

  }

  @Override
  public int getRotationAngle() {
    return 0;
  }

  @Override
  public int getX() {
    return 0;
  }

  @Override
  public int getY() {
    return 0;
  }

  @Override
  public void setX(int x) {

  }

  @Override
  public void setY(int y) {

  }

  @Override
  public int getWidth() {
    return 0;
  }

  @Override
  public int getHeight() {
    return 0;
  }

  @Override
  public void setWidth(int width) {

  }

  @Override
  public void setHeight(int height) {

  }

  @Override
  public void setAbsoluteSize(int width, int height) {

  }

  public static void main(String[] args) {
    MultileInheritance multileInheritance = new MultileInheritance();
    multileInheritance.rotateBy(180);
    multileInheritance.moveVertically(10);
    multileInheritance.methodDefault();
  }
}
