package com.lin.value;

import java.util.Calendar;

/**
 * @author lkmc2
 * @date 2018/10/5
 * @description 时间生成器
 */
public final class Times {

    // 日历
    private static final Calendar CALENDAR = Calendar.getInstance();

    /**
     * 创建指定时间的时间值
     * @param year 年
     * @param month 月
     * @param day 日
     * @return 时间值
     */
    public static long of(int year, int month, int day) {
        CALENDAR.set(year, month, day);
        return CALENDAR.getTimeInMillis();
    }

    /**
     * 创建指定时间的时间值
     * @param year 年
     * @param month 月
     * @param day 日
     * @param hour 时
     * @param minute 分
     * @param second 秒
     * @return 时间值
     */
    public static long of(int year, int month, int day, int hour, int minute, int second) {
        CALENDAR.set(year, month, day, hour, minute, second);
        return CALENDAR.getTimeInMillis();
    }

}
