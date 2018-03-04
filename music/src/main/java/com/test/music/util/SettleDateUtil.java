package com.test.music.util;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SettleDateUtil {

    /**
     * 根据账期的结束时间返回上一个账期的结束时间
     *
     * @param endDate 上一个账期的结束时间
     * @param settleAccounts 账期天数
     * @return
     */
    public static Date prevEndSettleDate(Date endDate, Integer settleAccounts) {
        if (settleAccounts.equals(30)) {
            return DateUtils.curMonthLastDay(DateUtils.prevMonth(DateUtils.setXDay(endDate, 1)));
        } else if (settleAccounts.equals(15)) {
            int day = DateUtils.getDay(endDate);
            //前半个月的开始时间,返回上个月的十六号
            if (day == 1) {
                return DateUtils.setXDay(DateUtils.prevMonth(endDate), 16);
            }
            // 前半个月的结束时间,返回上个月的最后一天
            else if (day == 15) {
                return DateUtils.curMonthLastDay(DateUtils.prevMonth(endDate));
            }
            // 后半个月的开始时间,返回当前月的第一天
            else if (day == 16) {
                return DateUtils.curMonthFirstDay(endDate);
            }
            // 后半个月的结束时间,返回当前月的第15天
            else {
                return DateUtils.setXDay(endDate, 15);
            }
        } else if (settleAccounts.equals(45)) {
            int day = DateUtils.getDay(endDate);
            if(day < 16) {
                /**
                 * 当前为15号,对应账期开始时间为上个月的1号,对应的上个账期的结束上上月的月末
                 */
                endDate = DateUtils.setXDay(endDate, 1);
                endDate = org.apache.commons.lang3.time.DateUtils.addMonths(endDate, -2);
                endDate = DateUtils.curMonthLastDay(endDate);
                return endDate;
            } else {
                /**
                 * 当前月的月末,对应的账期开始时间为上个月的16号,对应的上个月的账期的结束时间为上个月的15号
                 */
                return DateUtils.prevMonth(DateUtils.setXDay(endDate,15));// 上个月的15号
            }
        } else if (settleAccounts.equals(7)) {
            int day = DateUtils.getDay(endDate);
            if(day < 8){
                return DateUtils.curMonthLastDay(DateUtils.prevMonth(DateUtils.setXDay(endDate,1)));// 上个月的最后一天
            } else if (day < 15){
                return DateUtils.setXDay(endDate,7);// 当前月的7号
            } else if (day < 22){
                return DateUtils.setXDay(endDate,14);// 当前月的7号
            } else {
                return DateUtils.setXDay(endDate,21);// 当前月的21号
            }
        }
        else {
            throw new RuntimeException("暂时不支持该结算账期:" + settleAccounts);
        }
    }

    /**
     * 由账期的结束日期得到账期的开始日期
     *
     * @param endDate        账期的结束日期
     * @param settleAccounts
     * @return
     */
    public static Date countSettleBeginDate(Date endDate, Integer settleAccounts) {
        // 结算日期大于等于当前日期,那么返回账期开始时间
        int day = DateUtils.getDay(endDate);
        if (settleAccounts.equals(30)) {
            return DateUtils.curMonthFirstDay(endDate);
        } else if (settleAccounts.equals(15)) {
            //前半个月
            if (day == 15) {
                return DateUtils.curMonthFirstDay(endDate);
            } else {
                return DateUtils.setXDay(endDate, 16);
            }
        } else if (settleAccounts.equals(45)) {
            if(day == 15) {
                return DateUtils.prevMonth(DateUtils.setXDay(endDate,1));// 上个月的1号
            } else {
                return DateUtils.prevMonth(DateUtils.setXDay(endDate,16));// 上个月的16号
            }
        } else if (settleAccounts.equals(7)) {
            if(day == 7){
                return DateUtils.setXDay(endDate,1);
            } else if (day == 14){
                return DateUtils.setXDay(endDate,8);
            } else if (day == 21){
                return DateUtils.setXDay(endDate,15);
            } else {
                return DateUtils.setXDay(endDate,22);
            }
        } else {
            throw new RuntimeException("暂时不支持该结算账期:" + settleAccounts);
        }
    }


    /**
     * 根据账期和给定开始时间来计算当前日期对应的账期结束时间,支持45天
     *
     * @param startDate
     * @param settleAccounts
     * @return
     */
    public static Date countSettleEndDate(Integer settleAccounts,Date startDate) {
        // 得到当前对应的结算日期
        Date settleDate = countSettleDate(startDate, settleAccounts);
        return org.apache.commons.lang3.time.DateUtils.addDays(settleDate, -1);
    }

    /**
     * 根据账期来计算当前账期的结束日期,不支持苛选的45天
     *
     * @param settleAccounts
     * @return
     */
    public static Date countSettleEndDate(Integer settleAccounts) {
        return countSettleEndDate(settleAccounts,new Date());
    }

    /**
     * 计算当前日期对应的结算任务跑的日期
     *
     * @param startDate      合同开始时间
     * @param settleAccounts 账期时间
     * @return
     */
    public static Date countSettleDate(Date startDate, Integer settleAccounts) {
        return countSettleDate(startDate,settleAccounts,new Date());
    }

    /**
     * 根据跑定时任务的日期得到对应账期的结束日期
     *
     * @param settleDate      合同开始时间
     * @return
     */
    public static Date countEndDateBySettleDate(Date settleDate) {
        return org.apache.commons.lang3.time.DateUtils.addDays(settleDate,-1);
    }

    /**
     * 计算指定日期对应的结算任务跑的日期,要大于endDate
     * 目前用于计算合同最后一次跑的定时任务的时间
     * @param startDate
     * @param settleAccounts
     * @param endDate
     * @return
     */
    public static Date countSettleDate(Date startDate, Integer settleAccounts,Date endDate) {
        int i = 0;
        int max = 10000;
        Date retDate = DateUtils.clearTime(startDate);
        Date currentDate = endDate == null ? new Date() : endDate;
        currentDate = DateUtils.clearTime(currentDate);
        // 30 天账期的直接下个月一号
        if (settleAccounts.equals(30)) {
//            System.out.print(DateUtils.dateFormat(startDate) + "\t" +
//                    DateUtils.dateFormat(endDate)  + "\t" +
//                    settleAccounts + "|\t"
//            );
            do {
                retDate = DateUtils.nextMonthFirstDay(retDate);
                if (++i > max) {
                    throw new RuntimeException("没有找到对应的结算日期");
                }
//                System.out.print(DateUtils.dateFormat(retDate) + "\t");
            } while (retDate.compareTo(currentDate) < 1);
//            System.out.println();
            org.apache.commons.lang3.time.DateUtils.setDays(retDate, 1);
        }
        // 账期为15天的
        else if (settleAccounts.equals(15)) {
            /*
             * 1. 1-15号签合同,16号进行计算
             * 2. 16-31号签合同,1号进行计算
             */
            int day = 0;
            do {
                day = DateUtils.getDay(retDate);
                if (day > 15) {
                    retDate = DateUtils.nextMonthFirstDay(retDate);
                } else {
                    retDate = DateUtils.setXDay(retDate, 16);
                }
                if (++i > max) {
                    throw new RuntimeException("没有找到对应的结算日期");
                }
            } while (retDate.compareTo(currentDate) < 1);
        }
        // 账期为45天的
        else if (settleAccounts.equals(45)) {
            /**
             * 1号或者16号为账期的计算日期
             */
            int day = 0;
            do {
                day = DateUtils.getDay(retDate);
                if (day > 15) {
                    // 下下个月的一号
                    retDate = DateUtils.setXDay(retDate, 1);
                    retDate = org.apache.commons.lang3.time.DateUtils.addMonths(retDate,2);
                } else {
                    // 下个月的16号
                    retDate = DateUtils.setXDay(retDate, 16);
                    retDate = org.apache.commons.lang3.time.DateUtils.addMonths(retDate,1);
                }
                if (++i > max) {
                    throw new RuntimeException("没有找到对应的结算日期");
                }
            } while (retDate.compareTo(currentDate) < 1);
        }
        // 账期为7天的
        else if (settleAccounts.equals(7)) {
            int day = 0;
            do {
                day = DateUtils.getDay(retDate);
                if (day < 8) {
                    retDate = DateUtils.setXDay(retDate, 8);
                } else if(day < 15){
                    retDate = DateUtils.setXDay(retDate, 15);
                } else if(day < 22){
                    retDate = DateUtils.setXDay(retDate, 22);
                }
                else {
                    // 下个月的1号
                    retDate = DateUtils.setXDay(retDate, 1);
                    retDate = org.apache.commons.lang3.time.DateUtils.addMonths(retDate,1);
                }
                if (++i > max) {
                    throw new RuntimeException("没有找到对应的结算日期");
                }
            } while (retDate.compareTo(currentDate) < 1);
        }
        return retDate;
    }

    public static void main(String[] args) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    }
}
