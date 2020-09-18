package com.thoughtworks.capability.gtb;

import java.time.*;
import java.time.format.DateTimeFormatter;


public class MeetingSystemV3 {

  public static final ZoneId BEIJING_ZONEID = ZoneId.of("Asia/Shanghai");
  public static final ZoneId LONDON_ZONEID = ZoneId.of("Europe/London");
  public static final ZoneId CHICAGO_ZONEID = ZoneId.of("America/Chicago");

  public static void main(String[] args) {
    String timeStr = "2020-04-01 14:30:00";

    // 根据格式创建格式化类
    String pattern = "yyyy-MM-dd HH:mm:ss";

    DateTimeFormatter landonFormatter = DateTimeFormatter.ofPattern(pattern).withZone(LONDON_ZONEID);
    DateTimeFormatter chicagoFormatter = DateTimeFormatter.ofPattern(pattern).withZone(CHICAGO_ZONEID);
    // 从字符串解析得到会议时间
    ZonedDateTime landonMeetringZoneTime = ZonedDateTime.parse(timeStr, landonFormatter);
    ZonedDateTime beijinMeetingZoneTime = landonMeetringZoneTime.withZoneSameInstant(BEIJING_ZONEID);
    ZonedDateTime beijinNowTime = ZonedDateTime.now();
    if (beijinNowTime.isAfter(beijinMeetingZoneTime)) {
      ZonedDateTime tomorrow = beijinNowTime.plusDays(1);
      int newDayOfYear = tomorrow.getDayOfYear();
      beijinMeetingZoneTime = beijinMeetingZoneTime.withDayOfYear(newDayOfYear);
      ZonedDateTime chicagoMeetingZoneTime = beijinMeetingZoneTime.withZoneSameInstant(ZoneId.of("America/Chicago"));
      // 格式化新会议时间
      String showTimeStr = chicagoFormatter.format(chicagoMeetingZoneTime);
      System.out.println("next meeting time" + showTimeStr);
      Period between = Period.between(beijinNowTime.toLocalDate(), beijinMeetingZoneTime.toLocalDate());
      System.out.println("between,"+between);
    } else {
      System.out.println("metting is not start");
    }
  }
}
