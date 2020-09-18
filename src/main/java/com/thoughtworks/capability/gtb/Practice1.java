package com.thoughtworks.capability.gtb;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.MonthDay;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * 计算任意日期与下一个劳动节相差多少天
 *
 * @author itutry
 * @create 2020-05-15_16:33
 */
public class Practice1 {

  public static long getDaysBetweenNextLaborDay(LocalDate date) {
    int year = date.getYear();
    LocalDate lobar = getLobarDate(date, year);
    return date.until(lobar, ChronoUnit.DAYS);
  }

  private static LocalDate getLobarDate(LocalDate date, int year) {
    LocalDate lobar = LocalDate.of(year, Month.MAY, 1);
    long until = date.until(lobar, ChronoUnit.DAYS);
    if(until < 0) {
      lobar = LocalDate.of(year + 1, Month.MAY, 1);
    }
    return lobar;
  }
}
