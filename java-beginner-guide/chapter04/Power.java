// Power integer
class Power {
  double b;
  int e;
  double val;

  // Constructor of class
  Power(double base, int exp) {
    this.b = base;
    this.e = exp;

    this.val = 1;
    if (exp == 0) return;
    for ( ; exp > 0; exp--) this.val = this.val * base;
  }

  // Get rerult
  double getPower() {
    return this.val;
  }
}