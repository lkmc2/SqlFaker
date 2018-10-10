package com.lin.random.base;

import com.lin.random.RandomData;
import com.lin.utils.RandomUtils;

/**
 * 基础随机生成器
 * @author lkmc2
 * @since 1.0.0
 */
public abstract class BaseRandomCreator<T> implements RandomData<T> {

    @Override
    public T next() {
        // 创建候选值数组
        T[] array = createOptionsArray();

        // 从数组中随机选取一个值
        return RandomUtils.selectOneInArray(array);
    }

    /**
     * 创建候选值数组
     * @return 候选值数组
     */
    public abstract T[] createOptionsArray();

}
