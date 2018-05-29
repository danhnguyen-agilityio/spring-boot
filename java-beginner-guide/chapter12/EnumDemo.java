/** An enumeration of Transport varieties */
enum Transport {
  CAR, TRUCK, AIRPLANE, TRAIN, BOAT
}

/** Enumeration demo */
class EnumDemo {
  public static void main(String args[]) {
    Transport tp;

    tp = Transport.AIRPLANE;

    System.out.println(tp);
    System.out.println();

    tp = Transport.TRAIN;

    // Compare two enum values
    if (tp == Transport.TRAIN) {
      System.out.println("tp contain TRAIN");
    }

    // Use an enum to control a switch statement
    switch (tp) {
      case CAR:
        System.out.println("A CAR");
        break;
      case TRAIN:
        System.out.println("A TRAIN");
        break;
    }
  }
}