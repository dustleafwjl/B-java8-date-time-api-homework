package com.thoughtworks.capability.gtb;

import java.time.*;
import java.time.format.DateTimeFormatter;

/**
 * 脑洞会议系统v3.0
 * 1.当前会议时间"2020-04-01 14:30:00"表示伦敦的本地时间，而输出的新会议时间是芝加哥的本地时间
 *   场景：
 *   a:上个会议是伦敦的同事定的，他在界面上输入的时间是"2020-04-01 14:30:00"，所以我们要解析的字符串是伦敦的本地时间
 *   b:而我们在当前时区(北京时区)使用系统
 *   c:我们设置好新会议时间后，要发给芝加哥的同事查看，所以格式化后的新会议时间要求是芝加哥的本地时间
 * 2.用Period来实现下个会议时间的计算
 *
 * @author itutry
 * @create 2020-05-19_18:43
 */
public class MeetingSystemV3 {

  public static final ZoneId BEIJING_ZONEID = ZoneId.of("Asia/Beijing");
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
      System.out.println("下一次会议时间" + showTimeStr);
      Period between = Period.between(beijinNowTime.toLocalDate(), beijinMeetingZoneTime.toLocalDate());
      System.out.println("between,"+between);
    } else {
      System.out.println("会议还没开始呢");
    }
  }
}
