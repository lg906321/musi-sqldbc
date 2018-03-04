package com.test.music.util;



import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateUtils {
    public static final String TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static final String DATE_FORMAT = "yyyy-MM-dd";

    private static final String DATE_FORMAT2 = "yyyyMMdd";

    public static Date today() {
        Calendar time = Calendar.getInstance();
        time.set(Calendar.HOUR_OF_DAY, 0);
        time.set(Calendar.MINUTE, 0);
        time.set(Calendar.SECOND, 0);
        return time.getTime();
    }
    
    /***
    * @Title: todayForymd  
    * @Description: TODO(增加yyyyMMdd格式化的当前时间)  
    * @param @param date
    * @param @return    参数  
    * @return String    返回类型  
    * @throws
    */
    public static String todayForymd(Date date) {
    	DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT2);
        return dateFormat.format(date);
    }

    public static String timeFormat(Date date) {
        DateFormat dateFormat = new SimpleDateFormat(TIME_FORMAT);
        return dateFormat.format(date);
    }

    public static Date timeToDate(String date) {
        DateFormat dateFormat = new SimpleDateFormat(TIME_FORMAT);
        try {
            return dateFormat.parse(date);
        } catch (ParseException e) {
//            e.printStackTrace();
        }
        return null;
    }

    public static String dateFormat(Date date) {
        DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        return dateFormat.format(date);
    }

    public static Date dateFormat(String date) {
        DateFormat dateformat = new SimpleDateFormat(DATE_FORMAT);
        try {
            return dateformat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date dateFormatDate(Date date) {
        String dateStr = dateFormat(date);
        return dateFormat(dateStr);
    }

    public static Date datetimeFormat(String date) {
        DateFormat dateformat = new SimpleDateFormat(TIME_FORMAT);
        try {
            return dateformat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date tomorrow() {
        Calendar time = Calendar.getInstance();
        time.set(Calendar.DAY_OF_MONTH, time.get(Calendar.DAY_OF_MONTH) + 1);
        time.set(Calendar.HOUR_OF_DAY, 0);
        time.set(Calendar.MINUTE, 0);
        time.set(Calendar.SECOND, 0);
        return time.getTime();
    }

    public static Date before24() {
        Calendar time = Calendar.getInstance();
        time.set(Calendar.HOUR_OF_DAY, -24);
        time.set(Calendar.MINUTE, 0);
        time.set(Calendar.SECOND, 0);
        return time.getTime();
    }

    public static Date before96() {
        Calendar time = Calendar.getInstance();
        time.set(Calendar.HOUR_OF_DAY, -96);
        time.set(Calendar.MINUTE, 0);
        time.set(Calendar.SECOND, 0);
        return time.getTime();
    }

    public static Date toDate(String time) {
        DateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            return dateformat.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date toDate(String time, String format) {
        DateFormat dateformat = new SimpleDateFormat(format);
        try {
            return dateformat.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将当前时间格式化为：yyyy-MM-dd HH:mm:ss格式，再提取出其中的数字，组成数字日期格式。
     * 比如：2017-03-13 18:18:32，结果为20170313181832
     *
     * @return
     */
    public static String currentTimeNumberStr() {
        DateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = dateformat.format(new Date());

        String regEx = "[^0-9]";
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(date);
        String str = matcher.replaceAll("").trim();
        return str;
    }


    /**
     * 结果为20170313181832
     *
     * @return
     */
    public static String currentTimeNumberStrEasy() {
        DateFormat dateformat = new SimpleDateFormat("yyyyMMddHHmmss");
        String currentTimeNumber = dateformat.format(new Date());
        return currentTimeNumber;
    }

    /**
     * 结果为20170313181832
     *
     * @return
     */
    public static String currentTimeNumberStrEasy(Date date) {
        DateFormat dateformat = new SimpleDateFormat("yyyyMMddHHmmss");
        String currentTimeNumber = dateformat.format(date);
        return currentTimeNumber;
    }

    /**
     * 得到当前结算日期的核算日期
     *
     * @param begin          合同生效日期
     * @param settleAccounts 结算日期
     * @return
     */
    public static Date getCurSettleData(Date begin, int settleAccounts) {
        if (begin == null || settleAccounts < 1) {
            throw new NullPointerException();
        }
        int count = 10000;

        Date date = new Date();
        // 当前日期小鱼结算日期
        if (date.compareTo(begin) == -1) {
            throw new IllegalArgumentException("开始时间大于当前时间");
        }

        Date settle = null;
        // 当前时间等于或者小于结算日期都可以进行结算
        for (int i = 1; i <= count; i++) {
            if (date.compareTo((settle = org.apache.commons.lang3.time.DateUtils.addDays(date, settleAccounts))) < 1) {
                break;
            }
        }
        return settle;
    }


    /*
     * 获取当前年的最后一天日期
     */
    public static String getLastDay() {
        DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        Calendar currCal = Calendar.getInstance();
        int currentYear = currCal.get(Calendar.YEAR);
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, currentYear);
        calendar.roll(Calendar.DAY_OF_YEAR, -1);
        return dateFormat.format(calendar.getTime());
    }

    /**
     * 结果为201703
     *
     * @return
     */
    public static String currentTimeNumberStr(Date date) {
        DateFormat dateformat = new SimpleDateFormat("yyyyMM");
        String currentTimeNumber = dateformat.format(date);
        return currentTimeNumber;
    }

    /**
     * 将日期-8小时
     *
     * @param date
     * @return
     */
    public final static Date convertD2Date(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, cal.get(Calendar.HOUR_OF_DAY) - 8);
        return cal.getTime();
    }

    /**
     * 判断time是否在from，to之内
     *
     * @param time 指定日期
     * @param from 开始日期
     * @param to   结束日期
     * @return
     */
    public static boolean belongCalendar(Date time, Date from, Date to) {
        Calendar date = Calendar.getInstance();
        date.setTime(time);

        Calendar after = Calendar.getInstance();
        after.setTime(from);

        Calendar before = Calendar.getInstance();
        before.setTime(to);

        if (date.after(after) && date.before(before)) {
            return true;
        } else if (dateFormat(date.getTime()).equals(dateFormat(after.getTime())) || dateFormat(date.getTime())
                .equals(dateFormat(before.getTime()))) {
            return true;
        } else {
            return false;
        }
    }

    public static int getDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取下一个月的第一天
     *
     * @param date
     * @return
     */
    public static Date nextMonthFirstDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }

    /**
     * 获取指定日期的上个与的指定时间
     *
     * @param date
     * @return
     */
    public static Date prevMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1);
        return calendar.getTime();
    }

    /**
     * 获取指定日期的上个与的指定时间
     *
     * @param date
     * @return
     */
    public static Date nextMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1);
        return calendar.getTime();
    }


    /**
     * 修改当前日期的为日期n
     *
     * @param date
     * @param n
     * @return
     */
    public static Date setXDay(Date date, int n) {
        if (date == null || n < 0) {
            throw new RuntimeException("参数错误");
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, n);
        return calendar.getTime();
    }

    /**
     * 获取当前月份的最后一天
     *
     * @param date
     * @return
     */
    public static Date curMonthLastDay(Date date) {
        Calendar cale = Calendar.getInstance();
        cale.setTime(date);
        cale.add(Calendar.MONTH, 1);
        cale.set(Calendar.DAY_OF_MONTH, 0);
        return cale.getTime();
    }

    /**
     * 获取当前月份的第一天
     *
     * @param date
     * @return
     */
    public static Date curMonthFirstDay(Date date) {
        Calendar cale = Calendar.getInstance();
        cale.setTime(date);
        cale.add(Calendar.MONTH, 0);
        cale.set(Calendar.DAY_OF_MONTH, 1);
        return cale.getTime();
    }

    /**
     * 计算两个日期相差的天数,后边的日-前边的日期
     *
     * @return
     */
    public static int differentDays(Date date1, Date date2) {
        return (int) ((date2.getTime() - date1.getTime()) / (1000 * 3600 * 24));
    }

    /**
     * 计算两个日期相差的分钟,后边的日-前边的日期
     *
     * @return
     */
    public static int differentMinute(Date date1, Date date2) {
        return (int) ((date2.getTime() - date1.getTime()) / (1000 * 60));
    }

    /**
     * 计算传入日期距离当前日期多少天
     *
     * @param date
     * @return
     */
    public static int differentToday(Date date) {
        return differentDays(date, new Date());
    }


    public static String stringdateFormat(String date) {
        //String date = "Mon Mar 02 13:57:49 CST 2015";
        SimpleDateFormat sdf1 = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.UK);
        try {
            Date d = sdf1.parse(date);
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
            return sdf.format(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 清空指定日期的时间
     *
     * @param date 2017-01-01 10:12:12.234
     * @return 2017-01-01 00:00:00.000
     */
    public static Date clearTime(Date date) {
        Date date1 = org.apache.commons.lang3.time.DateUtils.setHours(date, 0);
        date1 = org.apache.commons.lang3.time.DateUtils.setMinutes(date1, 0);
        date1 = org.apache.commons.lang3.time.DateUtils.setSeconds(date1, 0);
        date1 = org.apache.commons.lang3.time.DateUtils.setMilliseconds(date1, 0);
        return date1;
    }

    public static List<String> displayDateEveryday(String dateFirst, String dateSecond) {
        List<String> dateList = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date dateOne = dateFormat.parse(dateFirst);
            Date dateTwo = dateFormat.parse(dateSecond);

            Calendar calendar = Calendar.getInstance();

            calendar.setTime(dateOne);

            while (calendar.getTime().before(dateTwo)) {
                calendar.add(Calendar.DAY_OF_MONTH, 1);
                System.out.println(dateFormat(calendar.getTime()));
                dateList.add(dateFormat(calendar.getTime()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dateList;
    }

    public static void main(String[] args) {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        System.out.println(format.format(date));
        Date date1 = setXDay(date, 20);
        System.out.println(format.format(date1));
        System.out.println(differentDays(date1,date));


    }
}