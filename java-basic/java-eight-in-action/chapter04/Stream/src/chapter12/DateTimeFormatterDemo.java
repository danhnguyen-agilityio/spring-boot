package chapter12;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.Date;
import java.util.Locale;

public class DateTimeFormatterDemo {
  public static void main(String[] args) {
    LocalDate date = LocalDate.of(2014, 3, 18);
    String s1 = date.format(DateTimeFormatter.BASIC_ISO_DATE);
    System.out.println(s1);

    String s2 = date.format(DateTimeFormatter.ISO_LOCAL_DATE);
    System.out.println(s2);

    LocalDate date1 = LocalDate.parse("20140318", DateTimeFormatter.BASIC_ISO_DATE);
    System.out.println(date1);

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    LocalDate date2 = LocalDate.of(2014, 3, 18);
    String formattedDate = date2.format(formatter);
    System.out.println(formattedDate);

    LocalDate date3 = LocalDate.parse(formattedDate, formatter);
    System.out.println(date3);

    DateTimeFormatter italianFormatter = DateTimeFormatter.ofPattern("d.MMMM yyyy", Locale.ITALIAN);
    LocalDate date4 = LocalDate.of(2014, 3, 18);
    String formattedDate1 = date4.format(italianFormatter);
    System.out.println(formattedDate1);

    LocalDate date5 = LocalDate.parse(formattedDate1, italianFormatter);
    System.out.println(date5);

    DateTimeFormatter italianFormatter1 = new DateTimeFormatterBuilder()
        .appendText(ChronoField.DAY_OF_MONTH)
        .appendLiteral(",")
        .appendText(ChronoField.MONTH_OF_YEAR)
        .appendLiteral(",")
        .appendText(ChronoField.YEAR)
        .parseCaseInsensitive()
        .toFormatter(Locale.ITALIAN);

    LocalDate date6 = LocalDate.of(2014, 3, 18);
    String formattedDate2 = date6.format(italianFormatter1);
    System.out.println(formattedDate2);

    LocalDate date7 = LocalDate.parse(formattedDate2, italianFormatter1);
    System.out.println(date7);
  }
}
