package com.lin.random.impl;

import com.lin.random.RandomData;
import com.lin.utils.RandomUtils;

/**
 * 随机年龄生成器（18到60岁）
 * @author lkmc2
 * @since 1.0.0
 */
public class AgeRandom implements RandomData<Integer> {

    @Override
    public Integer next() {
        return RandomUtils.nextIntRange(18, 60);
    }

}
