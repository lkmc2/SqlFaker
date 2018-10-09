package com.lin.random.impl;

import com.lin.random.base.BaseRandomCreator;

/**
 * @author lkmc2
 * @date 2018/10/3
 * @description 随机年龄生成器（18到60岁）
 * @since 1.0.0
 */
public class AgeRandom extends BaseRandomCreator<Integer> {

    @Override
    public Integer[] createOptionsArray() {
        Integer[] ages = new Integer[43];

        // 设置年龄范围为18到60岁
        for (int i = 18; i <= 60; i++) {
            ages[i - 18] = i;
        }

        return ages;
    }

}
