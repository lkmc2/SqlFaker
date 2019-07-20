package com.lin.generator;

import com.lin.random.RandomData;

/**
 * 顺序值生成器
 * @author lkmc2
 * @since 1.0.4
 */
public class Generator {

    /**
     * 创建可以指定可变参数中每一个值出现次数的生成器
     * @param valueList 值列表
     * @return 顺序值生成器
     */
    @SuppressWarnings("unchecked")
    public static <T> RepeatCountRandom of(T... valueList) {
        return new RepeatCountRandom(valueList);
    }

    /**
     * 创建 Integer 类型顺序值生成器
     * @param start 起始值
     * @return Integer 类型顺序值生成器
     */
    public static IntegerRandom ofInt(final int start) {
        return new IntegerRandom(start);
    }

    /**
     * 创建 Long 类型顺序值生成器
     * @param start 起始值
     * @return Long 类型顺序值生成器
     */
    public static LongRandom ofLong(final long start) {
        return new LongRandom(start);
    }

    /** 可以指定可变参数中每一个值重复出现次数的生成器 **/
    public static class RepeatCountRandom<T> implements RandomData<T> {

        /** 数组中每个值重复出现的次数 **/
        private int repeatCount = 1;

        /** 需要遍历的值数组 **/
        private T[] valueList;

        /** 当前数组的下标值 **/
        private int currentIndex = 0;

        /** 全部遍历的次数 **/
        private int totalIndex = 0;

        RepeatCountRandom(T[] valueList) {
            this.valueList = valueList;
        }

        public RandomData<T> repeatCount(int repeatCount) {
            this.repeatCount = repeatCount;
            return this;
        }

        @Override
        public T next() {
            // 获取数组中指定下标的值
            T value = valueList[currentIndex];

            // 如果遍历次数对允许重复的次数求余等于0,
            if (++totalIndex % repeatCount == 0) {
                // 数组下标增加
                currentIndex++;

                // 防止数组下标越界
                if (currentIndex >= valueList.length) {
                    currentIndex = 0;
                }
            }

            return value;
        }
    }

    /** Integer 类型值顺序生成器 **/
    public static class IntegerRandom implements RandomData<Integer> {

        private Integer startValue;

        public IntegerRandom(Integer startValue) {
            this.startValue = startValue;
        }

        @Override
        public Integer next() {
            return startValue++;
        }
    }

    /** Long 类型值顺序生成器 **/
    public static class LongRandom implements RandomData<Long> {

        private Long startValue;

        public LongRandom(Long startValue) {
            this.startValue = startValue;
        }

        @Override
        public Long next() {
            return startValue++;
        }
    }

}
