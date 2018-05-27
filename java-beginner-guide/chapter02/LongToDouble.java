/** Type conversion from long to double */
class LongToDouble {
  public static void main(String args[]) {
    long L;
    double D;
    
    L = 100123285L;
    D = L;
    System.out.println("L and D:" + L + " " + D); 
    
    long L1;
    double D1;
    D1 = 100123285.0;
    // L = D; Not convert double to long
    System.out.println("L and D: " + L + " " + D); 
  } 
}
