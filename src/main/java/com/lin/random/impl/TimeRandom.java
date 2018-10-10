package com.lin.random.impl;

import com.lin.random.RandomData;
import com.lin.utils.RandomUtils;

import java.util.Calendar;
import java.util.Date;

/**
 * 随机时间生成器（一年前到现在的时间范围内任意选择一个时刻）
 * @author lkmc2
 * @since 1.0.0
 */
public class TimeRandom implements RandomData<String> {

    // 日历
    private static final Calendar CALENDAR = Calendar.getInstance();

    @Override
    public String next() {
        // 获取当前时间
        Date date = new Date();

        CALENDAR.setTime(date);

        // 结束时间
        long endTime = CALENDAR.getTimeInMillis();

        // 当前时间减去一年，即一年前的时间
        CALENDAR.add(Calendar.YEAR, -1);

        // 开始时间
        long startTime = CALENDAR.getTimeInMillis();

        // 根据日期范围获取随机时间
        return RandomUtils.nextTimeRange(startTime, endTime);
    }

}
