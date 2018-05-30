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

    System.out.println("Here are all Transport constants");

    // Obtain an array of Transport constants
    Transport allTransports[] = Transport.values();
    for (Transport t : allTransports) {
      System.out.println(t);
    }

    System.out.println();

    tp = Transport.valueOf("AIRPLANE");
    System.out.println("Contains " + tp);
    System.out.println("------------------------");
    
    System.out.println("Ordinal values");
    Transport tp1, tp2, tp3;
    System.out.println("Their ordinal values");
    for (Transport t : Transport.values()) {
      System.out.println(t.ordinal());
    }

    tp1 = Transport.AIRPLANE;
    tp2 = Transport.TRAIN;
    tp3 = Transport.AIRPLANE;
    System.out.println();

    if (tp1.compareTo(tp2) < 0) {
      System.out.println(tp + " comes before " + tp2);
    }

    if (tp1.compareTo(tp2) > 0) {
      System.out.println(tp + " comes after " + tp2);
    }

    if (tp1.compareTo(tp3) == 0) {
      System.out.println(tp + " equals " + tp3);
    }
  }
}