/** Class series */
interface Series {
  void setCount(int c);

  int getCount();
}


class Shoes implements Series {
  int count;
  public void setCount(int c) {
    count = c;
  }
  public int getCount() {
    return count;
  }
}

class SeriesDemo {
  public static void main(String args[]) {
    Shoes shoe = new Shoes();
    Series series;
    
    series = shoe;
    series.setCount(100);

    System.out.println(series.getCount());
  }
}