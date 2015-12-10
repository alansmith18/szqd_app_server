package com.szqd.framework.util;

import org.apache.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by like on 8/3/15.
 */
public class DateUtils {

    private static final Logger LOGGER = Logger.getLogger(DateUtils.class);

    public static Date stringToDate(String dateString, String pattern)
    {
        SimpleDateFormat sf = new SimpleDateFormat(pattern);
        try {
            Date date = sf.parse(dateString);
            return date;
        } catch (ParseException e) {
            LOGGER.error("com.szqd.framework.util.DateUtils.truncateDate(java.lang.String date, java.lang.String pattern)",e);
            return null;
        }
    }

    public static String dateToString(Date date, String pattern)
    {
        SimpleDateFormat sf = new SimpleDateFormat(pattern);
        String dateString = sf.format(date);
        return dateString;
    }

    public static Calendar stringToCalendar(String dateString, String pattern)
    {
        Date date = stringToDate(dateString, pattern);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    public static Calendar truncateDate(Calendar calendar)
    {
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar;
    }

    public static Calendar truncateDate(Date date)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return truncateDate(calendar);
    }

    public static Calendar truncateDate(String dateString,String pattern)
    {
        Date date = stringToDate(dateString, pattern);
        return truncateDate(date);
    }

    public static Calendar roundDate(Calendar calendar)
    {

        calendar.set(Calendar.HOUR,0);
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        return calendar;
    }

    public static Calendar roundDate(Date date)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return roundDate(calendar);
    }

    public static Calendar roundDate(String dateString,String pattern)
    {
        Date date = stringToDate(dateString, pattern);
        return roundDate(date);
    }

    public static Calendar getTomorrowForSpecifiedTime(int hour,int minute,int second,int millisecond){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,hour);
        calendar.set(Calendar.MINUTE,minute);
        calendar.set(Calendar.SECOND,second);
        calendar.set(Calendar.MILLISECOND,millisecond);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        return calendar;
    }

    public static Calendar getTodayForSpecifiedTime(int hour,int minute,int second,int millisecond)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,hour);
        calendar.set(Calendar.MINUTE,minute);
        calendar.set(Calendar.SECOND,second);
        calendar.set(Calendar.MILLISECOND,millisecond);
        return calendar;
    }

    public static Long stringToLong(String dateString, String pattern)
    {
        Date date = stringToDate(dateString, pattern);
        if (date != null) return date.getTime();
        else return null;
    }

    public static Long daysBetweenTwoDate(Date begin,Date end)
    {
        if (end == null || begin == null){
            LOGGER.debug("日期参数为null");
            throw new RuntimeException("日期参数为null");
        }

        long diff = end.getTime() - begin.getTime();
        return TimeUnit.DAYS.convert(diff,TimeUnit.MILLISECONDS);
    }
}
