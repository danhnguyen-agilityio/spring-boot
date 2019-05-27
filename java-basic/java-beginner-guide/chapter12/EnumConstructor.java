/** Use an enum constructor, instance variable and method */
enum Transport {
  CAR(65), TRUCK(55), AIRPLANE(600), TRAIN(700), BOAT(22);

  private int speed;

  /** Constructor */
  Transport(int s ) {
    speed = s;
  }

  /** Get speed of transport */
  int getSpeed() {
    return speed;
  }
}

/** Enum constructor demo */
class EnumConstructor {
  public static void main(String args[]) {
    Transport tp;

    System.out.println("All transport speeds: ");
    for (Transport t : Transport.values()) {
      System.out.println(t + " typical speed is " + t.getSpeed());
    }
  }
}