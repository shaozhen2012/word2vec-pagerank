package com.rongpingkeji.common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DateUtils extends org.apache.commons.lang3.time.DateUtils {

    public static final String yyyy_MM_dd_EN = "yyyy-MM-dd";
    public static final String yyyy_MM_dd_HH_mm_ss_EN = "yyyy-MM-dd HH:mm:ss";
    public static final String yyyyMMddHHmmss = "yyyyMMddHHmmss";
    public static final String yyyyMMddmm = "yyyyMMddHHmm";
    public static final String yyyyMMdd = "yyyyMMdd";
    public static final String yyyy_MM_dd_CN = "yyyy年MM月dd日";
    public static final String yyyy_MM_dd_HH_mm_ss_CN = "yyyy年MM月dd日HH时mm分ss秒";
    private static Map<String, DateFormat> dateFormatMap = new HashMap<String, DateFormat>();


    public static String mysqlDateStr(String key) {
        return "date_format(" + key + ",'%Y-%m-%d %H:%i:%s')";
    }


    public static String getMysqlDateString() {
        return dateToString(new Date(), DateUtils.yyyy_MM_dd_HH_mm_ss_EN);
    }

    public static String getMysqlDateString(Date date) {
        return dateToString(date, DateUtils.yyyy_MM_dd_HH_mm_ss_EN);
    }


    public static Date getToday() {
        return stringToDate(dateToString(new Date(), yyyy_MM_dd_EN) + " 00:00:00");
    }


    /**
     * 功能描述：获取时间格式化对象
     *
     * @param formatStr
     * @return
     */
    public static DateFormat getDateFormat(String formatStr) {
        DateFormat df = dateFormatMap.get(formatStr);
        if (df == null) {
            df = new SimpleDateFormat(formatStr);
            dateFormatMap.put(formatStr, df);
        }
        return df;
    }

    /**
     * 功能描述：根据时间格式获取当前时间
     *
     * @param date 当前时间
     * @return
     */
    public static String dateToString(Date date) {
        return dateToString(date, yyyy_MM_dd_HH_mm_ss_EN);
    }

    /**
     * 功能描述：根据时间格式获取当前时间
     *
     * @param date          当前时间
     * @param dateFormatStr 时间格式化字符串
     * @return
     */
    public static String dateToString(Date date, String dateFormatStr) {
        DateFormat format = getDateFormat(dateFormatStr);
        if (date != null) {
            return format.format(date);
        }
        return null;
    }

    public static Date getNowTime() {
        return Calendar.getInstance().getTime();
    }

    /**
     * 功能描述：将字符串转为时间
     *
     * @param dateTimeStr 当前时间
     * @return
     */
    public static Date stringToDate(String dateTimeStr) {
        return stringToDate(dateTimeStr, yyyy_MM_dd_HH_mm_ss_EN);
    }

    /**
     * 功能描述：将字符串转为时间
     *
     * @param dateTimeStr   当前时间
     * @param dateFormatStr 时间格式化字符串
     * @return
     */
    public static Date stringToDate(String dateTimeStr, String dateFormatStr) {
        try {
            if ((dateTimeStr == null) || (dateTimeStr.equals(""))) {
                return null;
            }
            DateFormat format = getDateFormat(dateFormatStr);
            Date date = format.parse(dateTimeStr);
            return date;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static String weekdayOf(Date date) {
        final String dayNames[] = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (dayOfWeek < 0) {
            dayOfWeek = 0;
        }
        return dayNames[dayOfWeek];
    }

    public static Long getTimestamp() {
        return System.currentTimeMillis();
    }
}
