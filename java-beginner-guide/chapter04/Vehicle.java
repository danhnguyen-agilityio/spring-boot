// This is a constructor for Vehicle
class Vehicle {
  int passengers;
  int fuelcap;
  int mpg;

  Vehicle(int p, int f, int m) {
    passengers = p;
    fuelcap = f;
    mpg = m;
  }

  int range() {
    return mpg * fuelcap;
  }

  // Compute fuel needed for a given distance
  double fuelneed(int miles) {
    return (double) miles / mpg;
  }
}