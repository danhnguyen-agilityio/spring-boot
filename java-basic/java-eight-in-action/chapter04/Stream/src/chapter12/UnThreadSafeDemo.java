package chapter12;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

class DateFormatAccess {
  private static DateFormat df = new SimpleDateFormat("yyyy MM dd");

//  synchronized
  public static Date convertStringToDate(String dateString) throws ParseException {
    return df.parse(dateString); // occur not thread safe and thrown exception when multi thread use same df
//    return new SimpleDateFormat("yyyy MM dd").parse(dateString) // thread safe because not share df
  }
}

class ThreadDemo implements Runnable {

  @Override
  public void run() {
    try {
      Date x = new DateFormatAccess().convertStringToDate("2017 02 11");
      System.out.println(x);
      Date y = DateFormatAccess.convertStringToDate("2017 02 11");
      System.out.println(y);
    } catch (ParseException e) {
      e.printStackTrace();
    }
  }
}

// old API, java.util.Date, DateFormat, SimpleDateFormat
public class UnThreadSafeDemo {
  public static void main(String[] args) {
    Thread t1 = new Thread(new ThreadDemo());
    Thread t2 = new Thread(new ThreadDemo());
    t1.start();
    t2.start();
  }
}
