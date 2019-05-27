/** Demo returning of method */
class RetMeth {
  public static void main(String args[]) {
    Vehicle minivan = new Vehicle(7, 16, 21);
    Vehicle sportscar = new Vehicle(2, 15, 12);
    double gallons;
    int dist = 252;

    int range1, range2;

    range1 = minivan.range();
    range2 = sportscar.range();

    gallons = minivan.fuelneed(dist);
    System.out.println("To go " + dist + " mils minivan needs " + gallons + " gallons of fuel.");

    System.out.println("Minivan can carray " + minivan.passengers + " with range of " + range1 + " Miles");
    System.out.println("Sportscar can carray " + sportscar.passengers + " with range of " + range2 + " Miles");
  }
}