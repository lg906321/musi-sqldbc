package com.test.music.util;


import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

public final class DateUtil {
    private DateUtil() {
    }


    public static LocalDateTime LocalDate(){
        return LocalDateTime.now();
    }


    public static void main(String[] args) {
        System.out.println(LocalDateTime.now());
        LocalDate today = LocalDate.now(); // ->
        /*
        System.out.println(today);

        LocalDate crischristmas = LocalDate.of(2014, 12, 25); // -> 2014-12-25
        System.out.println(crischristmas);

        LocalDate endOfFeb = LocalDate.parse("2014-02-28"); // 严格按照ISO yyyy-MM-dd验证，02写成2都不行，当然也有一个重载方法允许自己定义格式
        System.out.println(endOfFeb);

        LocalDate firstDayOfThisMonth = today.with(TemporalAdjusters.firstDayOfMonth()); // 2014-12-01
        System.out.println(firstDayOfThisMonth);
        */
        // 取本月第2天：
        LocalDate secondDayOfThisMonth = today.withDayOfMonth(2); // 2014-12-02
        System.out.println(secondDayOfThisMonth);

        // 取本月最后一天，再也不用计算是28，29，30还是31：
        LocalDate lastDayOfThisMonth = today.with(TemporalAdjusters.lastDayOfMonth()); // 2014-12-31
        System.out.println(lastDayOfThisMonth);

        // 取下一天：
        LocalDate firstDayOf2015 = lastDayOfThisMonth.plusDays(1); // 变成了2015-01-01
        System.out.println(firstDayOf2015);

        // 取2015年1月第一个周一，这个计算用Calendar要死掉很多脑细胞：
        LocalDate firstMondayOf2015 = LocalDate.parse("2015-01-01").with(TemporalAdjusters.firstInMonth(DayOfWeek.MONDAY)); // 2015-01-05
        System.out.println(firstMondayOf2015);

    }


}