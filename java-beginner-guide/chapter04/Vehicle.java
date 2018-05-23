// page 183
class Vehicle {
  int passengers;
  int fuelcap;
  int mpg;

  int range() {
    return mpg * fuelcap;
  }

  // Compute fuel needed for a given distance
  double fuelneed(int miles) {
    return (double) miles / mpg;
  }
}