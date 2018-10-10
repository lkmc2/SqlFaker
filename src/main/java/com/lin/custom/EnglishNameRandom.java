package com.lin.custom;

import com.lin.random.RandomData;
import com.lin.utils.RandomUtils;

/**
 * 英文名随机生成器
 * @author lkmc2
 * @since 1.0.0
 */
public class EnglishNameRandom implements RandomData<String> {
    @Override
    public String next() {
        int random = RandomUtils.randomByLength(3);
        return new String[]{"Kim Lily", "Andy Wang", "July Six"}[random];
    }
}
