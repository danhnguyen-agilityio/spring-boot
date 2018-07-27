package chapter12;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

class DateFormatAccessSafe {
  private static DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

  public static LocalDateTime convertStringToDate(String dateString) {
    return LocalDateTime.parse(dateString, df);
  }
}

class ThreadDemoSafe implements Runnable {

  @Override
  public void run() {
    LocalDateTime x = new DateFormatAccessSafe().convertStringToDate("1993-02-11 11:00:00");
    System.out.println(x);
    LocalDateTime y = DateFormatAccessSafe.convertStringToDate("1993-02-11 11:00:00");
    System.out.println(y);
  }
}

// new API java 8
public class ThreadSafe {
  public static void main(String[] args) {
    Thread t1 = new Thread(new ThreadDemoSafe());
    Thread t2 = new Thread(new ThreadDemoSafe());
    t1.start();
    t2.start();
  }
}
