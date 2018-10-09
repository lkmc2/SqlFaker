package com.lin.utils;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Random;

/**
 * @author lkmc2
 * @date 2018/10/4
 * @description 随机数工具
 * @since 1.0.0
 */
public final class RandomUtils {

    // 随机数生成器
    private static final Random RANDOM = new Random();

    // 日期格式转换器
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 根据长度自动生成随机数
     * @param len 长度
     * @return [0,len)范围内的随机数
     */
    public static int randomByLength(int len) {
        return RANDOM.nextInt(len);
    }

    /**
     * 在数组中随机选取一个值
     * @param array 数据
     * @param <T> 数据类型
     * @return 数据中任意一个值
     */
    public static <T> T selectOneInArray(T[] array) {
        // 根据数组长度自动生成随机数
        int index = randomByLength(array.length);
        return array[index];
    }

    /**
     * 检查数字的合法性
     * @param start 起始值
     * @param end 结束值
     */
    private static void checkNumberValid(Double start, Double end) {
        if (start > end) {
            throw new RuntimeException("起始值不能大于结束值");
        }

        if (start < 0) {
            throw new RuntimeException("起始值必须大于0");
        }
    }

    /**
     * 根据整型范围获取随机数
     * @param start 起点值
     * @param end 终点值
     * @return [start, end]范围内的随机数
     */
    public static int nextIntRange(int start, int end) {
        checkNumberValid((double) start, (double) end);

        return RANDOM.nextInt(end - start + 1) + start;
    }

    /**
     * 根据长整型范围获取随机数
     * @param start 起点值
     * @param end 终点值
     * @return [start, end)范围内的随机数
     */
    public static long nextLongRange(long start, long end) {
        return start == end ? start : (long) (start + (end - start) * RANDOM.nextDouble());
    }

    /**
     * 根据单浮点型范围获取随机数，小数精度为2
     * @param start 起点值
     * @param end 终点值
     * @return [start, end]范围内的随机数
     */
    public static float nextFloatRange(float start, float end) {
        return nextFloatRange(start, end, 2);
    }

    /**
     * 根据单浮点型范围获取随机数，可以设置小数精度
     * @param start 起点值
     * @param end 终点值
     * @param precision 小数点精度（最大6位数）
     * @return [start, end]范围内的随机数
     */
    public static float nextFloatRange(float start, float end, int precision) {
        checkNumberValid((double) start, (double) end);

        if (start == end) {
            return start;
        }

        // 整数部分的值
        int intValue = nextIntRange((int) start, (int) end);

        // 生成一个随机浮点数（共7位小数）
        BigDecimal bd = new BigDecimal(RANDOM.nextFloat());

        // 设置精度，向上取整
        bd = bd.setScale(precision, BigDecimal.ROUND_FLOOR);

        // 小数部分的值
        float floatValue = bd.floatValue();

        // 获取 整型 + 浮点型 的值
        float resultValue = intValue + floatValue;

        // 防止结果越界
        resultValue = (resultValue < start) ? start : resultValue;
        resultValue = (resultValue > end) ? end : resultValue;

        return resultValue;
    }

    /**
     * 根据双浮点型范围获取随机数，小数精度为2
     * @param start 起点值
     * @param end 终点值
     * @return [start, end]范围内的随机数
     */
    public static double nextDoubleRange(double start, double end) {
        return nextDoubleRange(start, end, 2);
    }

    /**
     * 根据双浮点型范围获取随机数，可以设置小数精度
     * @param start 起点值
     * @param end 终点值
     * @param precision 小数点精度（最大6位数）
     * @return [start, end]范围内的随机数
     */
    public static double nextDoubleRange(double start, double end, int precision) {
        checkNumberValid(start, end);

        if (start == end) {
            return start;
        }

        // 整数部分的值
        int intValue = nextIntRange((int) start, (int) end);

        // 生成一个随机浮点数（共7位小数）
        BigDecimal bd = new BigDecimal(RANDOM.nextDouble());

        // 设置精度，向上取整
        bd = bd.setScale(precision, BigDecimal.ROUND_FLOOR);

        // 小数部分的值
        double doubleValue = bd.doubleValue();

        // 获取 整型 + 浮点型 的值
        double resultValue = intValue + doubleValue;

        // 防止结果越界
        resultValue = (resultValue < start) ? start : resultValue;
        resultValue = (resultValue > end) ? end : resultValue;

        return resultValue;
    }

    /**
     * 根据日期范围获取随机时间
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 格式化后的时间字符串，字符串格式：yyyy-MM-dd HH:mm:ss
     */
    public static String nextTimeRange(long startTime, long endTime) {
        checkNumberValid((double) startTime, (double) endTime);

        // 获取指定范围内的长整型值
        long timeValue = nextLongRange(startTime, endTime);

        // 格式化时间成字符串
        return DATE_FORMAT.format(timeValue);
    }

}
