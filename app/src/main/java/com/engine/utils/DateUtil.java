package com.engine.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * <p>
 * 功能:日期时间工具类
 * </p>
 *
 * @author flydr
 */
public class DateUtil {

    /**
     * 年
     */
    public static final int YEAR = Calendar.YEAR;
    /**
     * 月
     */
    public static final int MONTH = Calendar.MONTH;
    /**
     * 日
     */
    public static final int DATE = Calendar.DATE;
    /**
     * 时
     */
    public static final int HOUR = Calendar.HOUR;
    /**
     * 分钟
     */
    public static final int MINUTE = Calendar.MINUTE;
    /**
     * 秒
     */
    public static final int SECOND = Calendar.SECOND;

    private DateUtil() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 获得指定日期的字符串形式
     *
     * @param date    指定日期
     * @param formate 日期格式
     * @return
     */
    public static String getDateStr(Date date, String formate) {
        String result = null;
        if (date == null) {
            return null;
        }
        SimpleDateFormat dateformate = new SimpleDateFormat(formate);
        result = dateformate.format(date);
        return result;
    }

    /**
     * 将指定格式的日期字符串转换为新的日期格式字符串
     *
     * @param dateStr    日期字符串
     * @param srcFormat  源日期格式
     * @param destFormat 目标日期格式
     * @return
     * @throws ParseException
     */
    public static String getDateStr(String dateStr, String srcFormat, String destFormat) throws ParseException {
        SimpleDateFormat dateformate = new SimpleDateFormat(srcFormat);
        String str = null;
        Date date = dateformate.parse(dateStr);
        dateformate = new SimpleDateFormat(destFormat);
        str = dateformate.format(date);
        return str;
    }

    /**
     * 将日期字符串转换日期类型
     *
     * @param dateStr 日期字符串
     * @param format  日期格式
     * @return
     * @throws ParseException
     */
    public static Date getDate(String dateStr, String format) throws ParseException {
        Date date = null;
        if (dateStr == null || dateStr.equals("")) {
            return null;
        }
        SimpleDateFormat dateformate = new SimpleDateFormat(format);

        date = dateformate.parse(dateStr);
        return date;
    }

    /**
     * 取得服务器的日期字符串，日期格式为“yyyy-MM-dd”
     *
     * @return 日期字符串
     */
    public static String getSysDateStr() {
        return DateUtil.getDateStr(new Date(), "yyyy-MM-dd");
    }

    /**
     * 取得服务器的日期时间字符串，日期格式为"MM-dd HH:mm"
     *
     * @return 日期字符串
     */
    public static String getSysDateConciseStr() {
        return DateUtil.getDateStr(new Date(), "MM-dd HH:mm");
    }

    /**
     * 取得服务器的日期时间字符串，日期格式为“yyyy-MM-dd HH:mm:ss”
     *
     * @return 日期字符串
     */
    public static String getSysDateTimeStr() {
        return DateUtil.getDateStr(new Date(), "yyyy-MM-dd HH:mm:ss");
    }


    /**
     * 时间加法（分钟）
     *
     * @param date
     * @param timeLen
     * @param type
     * @return
     */
    public static Date addTime(Date date, int timeLen, int type) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(type, timeLen);
        return calendar.getTime();
    }


    /**
     * 时间格式转换
     *
     * @param time
     * @return
     */
    public static String formatTime(long time) {

        time /= 1000;
        long minute = time / 60;
        // int hour = minute / 60;
        long second = time % 60;
        minute %= 60;
        return String.format("%02d:%02d", minute, second);
    }


    /**
     * 计算时间相差的天数
     *
     * @param currentTime
     * @param oldTime
     * @return
     */
    public static int daysOfDate(long currentTime, long oldTime) {
        long time = currentTime - oldTime;
        long days = time / (1000 * 3600 * 24);
        return Integer.valueOf(days + "");
    }

    /**
     * 获取超时时间
     *
     * @param currentTime
     * @return
     */
    public static long getTimeOutFormCurentDate(long currentTime) {
        return currentTime - (1000 * 3600 * 24) * 7;
    }
}
