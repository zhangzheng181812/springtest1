package com.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;

/**
 * @Author : WANG CHUNYANG
 * @Date : 2018/4/11
 * @Description :
 */
public class DateUtil {

    public static SimpleDateFormat sdfd = new SimpleDateFormat("yyyy-MM-dd");
    public static SimpleDateFormat sdfdymd = new SimpleDateFormat("yyyyMMdd");
    public static SimpleDateFormat ymdhms = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");


    /**
     * 根据指定时间获取下个月时间
     * @return
     * @throws Exception
     */
    public static Date getNextMon(Date date) throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH,1);
        Date expiredTime = calendar.getTime();
        return expiredTime;
    }

    /**
     * 时间类型  yyyyMMdd 转 yyyy-MM-dd
     * @param dateStr
     * @return
     */
    public static String changeFormat(String dateStr) {
        Date date = null;
        try {
            date = sdfdymd.parse(dateStr);
           return  sdfd.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateStr;
    }

    /**
     * 时间类型 转yyyy-MM-dd 格式
     * @param date
     * @return
     */
    public static String getFormateString(Date date) {
        if(date==null){
            return "";
        }
        String time = sdfd.format(date);
        return time;
    }

    /**
     * 时间类型 转 yyyy-MM-dd HH:mm:ss
     * @param date
     * @return
     */
    public static String getFormateStringForYMDHMS(Date date) {
        if(date==null){
            return "";
        }
        String time = ymdhms.format(date);
        return time;
    }
    /**
     * 功能：当前时间增加年数。
     *
     * @param years 正值时时间延后，负值时时间提前。
     * @return Date
     */
    public static Date addYears(int years) {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.set(Calendar.YEAR, c.get(Calendar.YEAR) + years);
        return new Date(c.getTimeInMillis());
    }

    /**
     * 功能：增加年数。
     * @param years 正值时时间延后，负值时时间提前。
     * @return Date
     */
    public static Date addYears(Date date,int years) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.YEAR, c.get(Calendar.YEAR) + years);
        return new Date(c.getTimeInMillis());
    }

    /**
     * 功能：当前时间增加月数。
     *
     * @param months 正值时时间延后，负值时时间提前。
     * @return Date
     */
    public static Date addMonths(int months) {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.set(Calendar.MONTH, c.get(Calendar.MONTH) + months);
        return new Date(c.getTimeInMillis());
    }

    /**
     * 功能：获取某个月后的最后一天
     *
     * @param months 正值时时间延后，负值时时间提前。
     * @return Date
     */
    public static Date getLastDayOfMonth(int months) {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.set(Calendar.MONTH, c.get(Calendar.MONTH) + months);
        //获取某月最大天数
        int lastDay = c.getActualMaximum(Calendar.DAY_OF_MONTH);
        c.set(Calendar.DAY_OF_MONTH, lastDay);
        return new Date(c.getTimeInMillis());
    }

    /**
     * 功能：当前时间增加天数。
     *
     * @param days 正值时时间延后，负值时时间提前。
     * @return Date
     */
    public static Date addDays(int days) {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.set(Calendar.DATE, c.get(Calendar.DATE) + days);
        return new Date(c.getTimeInMillis());
    }

    /**
     * 获取本月第一天
     * @return
     */
    public static String firstDayInMonth(){
        LocalDate date = LocalDate.now();
        LocalDate firstDayOfMonth = date.with(TemporalAdjusters.firstDayOfMonth());
        return firstDayOfMonth.format(dtf);
    }

    /**
     * 获取本月最后一天
     * @return
     */
    public static String lastDayInMonth(){
        LocalDate date = LocalDate.now();
        LocalDate lastDayOfMonth = date.with(TemporalAdjusters.lastDayOfMonth());
        return lastDayOfMonth.format(dtf);
    }

    /**
     * 今天
     * @return
     */
    public static String todayInMonth(){
        LocalDate date = LocalDate.now();
        return date.format(dtf);
    }


}
