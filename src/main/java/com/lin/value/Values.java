package com.lin.value;

import com.lin.random.RandomData;
import com.lin.random.base.BaseRandomCreator;
import com.lin.utils.RandomUtils;

/**
 * @author lkmc2
 * @date 2018/10/5
 * @description 值的范围
 */
public final class Values {

    /**
     * 设置候选值数组
     * @param data 候选值数组
     * @return 随机值生成器
     */
    public static <T> RandomData<T> of(final T... data) {
        return new BaseRandomCreator<T>() {
            @Override
            public T[] createOptionsArray() {
                return data;
            }
        };
    }

    /**
     * 设置整形值范围
     * @param start 起始值
     * @param end 结束值
     * @return 随机值生成器
     * 生成范围为：[start, end]，包括起点到终点的值
     */
    public static RandomData<Integer> ofIntRange(final int start, final int end) {
        return new RandomData<Integer>() {
            @Override
            public Integer next() {
                // 根据整形范围获取随机数
                return RandomUtils.nextIntRange(start, end);
            }
        };
    }

    /**
     * 设置长整形值范围
     * @param start 起始值
     * @param end 结束值
     * @return 随机值生成器
     * 生成范围为：[start, end)，包括起点到终点的值
     */
    public static RandomData<Long> ofLongRange(final long start, final long end) {
        return new RandomData<Long>() {
            @Override
            public Long next() {
                // 根据长整形范围获取随机数
                return RandomUtils.nextLongRange(start, end);
            }
        };
    }

    /**
     * 设置单精度浮点形值范围
     * @param start 起始值
     * @param end 结束值
     * @return 随机值生成器
     * 生成范围为：[start, end]，包括起点到终点的值
     */
    public static RandomData<Float> ofFloatRange(final float start, final float end) {
        return new RandomData<Float>() {
            @Override
            public Float next() {
                // 根据单精度浮点形范围获取随机数
                return RandomUtils.nextFloatRange(start, end);
            }
        };
    }

    /**
     * 设置单精度浮点形值范围
     * @param start 起始值
     * @param end 结束值
     * @param precision 小数点精度（最大6位数）
     * @return 随机值生成器
     * 生成范围为：[start, end]，包括起点到终点的值
     */
    public static RandomData<Float> ofFloatRange(final float start, final float end, final int precision) {
        return new RandomData<Float>() {
            @Override
            public Float next() {
                // 根据单精度浮点形范围获取随机数
                return RandomUtils.nextFloatRange(start, end, precision);
            }
        };
    }

    /**
     * 设置双精度浮点形值范围
     * @param start 起始值
     * @param end 结束值
     * @return 随机值生成器
     * 生成范围为：[start, end]，包括起点到终点的值
     */
    public static RandomData<Double> ofDoubleRange(final double start, final double end) {
        return new RandomData<Double>() {
            @Override
            public Double next() {
                // 根据双精度浮点形范围获取随机数
                return RandomUtils.nextDoubleRange(start, end);
            }
        };
    }

    /**
     * 设置双精度浮点形值范围
     * @param start 起始值
     * @param end 结束值
     * @param precision 小数点精度（最大6位数）
     * @return 随机值生成器
     * 生成范围为：[start, end]，包括起点到终点的值
     */
    public static RandomData<Double> ofDoubleRange(final double start, final double end, final int precision) {
        return new RandomData<Double>() {
            @Override
            public Double next() {
                // 根据双精度浮点形范围获取随机数
                return RandomUtils.nextDoubleRange(start, end, precision);
            }
        };
    }

    /**
     * 设置时间值范围
     * @param startTime 起始时间
     * @param endTime 结束时间
     * @return 随机值生成器
     */
    public static RandomData<String> ofTimeRange(final long startTime, final long endTime) {
        return new RandomData<String>() {
            @Override
            public String next() {
                // 根据日期范围获取随机时间
                return RandomUtils.nextTimeRange(startTime, endTime);
            }
        };
    }

}
