package com.lin.custom;

import com.lin.random.RandomData;
import com.lin.utils.RandomUtils;

/**
 * @author lkmc2
 * @date 2018/10/4
 * @description 英文名随机生成器
 */
public class EnglishNameRandom implements RandomData<String> {
    @Override
    public String next() {
        int random = RandomUtils.randomByLength(3);
        return new String[]{"Kim Lily", "Andy Wang", "July Six"}[random];
    }
}
