// page 183
class Vehicle {
  int passengers;
  int fuelcap;
  int mpg;

  int range() {
    return mpg * fuelcap;
  }
}