package data;

import java.util.Arrays;

public enum Month {

  JANUARY("января", 1),
  FEBRUARY("февраля", 2),
  MARCH("марта", 3),
  APRIL("апреля", 4),
  MAY("мая", 5),
  JUNE("июня", 6),
  JULY("июля", 7),
  AUGUST("августа", 8),
  SEPTEMBER("сентябр", 9),
  OCTOBER("октября", 10),
  NOVEMBER("ноября", 11),
  DECEMBER("декабря", 12);


  private String monthName;
  private int monthNumber;

  Month(String monthName, int monthNumber){
    this.monthName = monthName;
    this.monthNumber = monthNumber;
  }


  public String getMonthName() {
    return monthName;
  }

  public int getNumber(){
    return this.monthNumber;
  }

  public static int getMonthNumber(String monthName){

    return Arrays.stream(Month.values())
        .filter((Month month) -> monthName.contains(month.getMonthName()))
        .findFirst()
        .get()
        .getNumber();

  }
}
