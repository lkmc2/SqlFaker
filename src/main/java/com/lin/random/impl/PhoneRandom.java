package com.lin.random.impl;

import com.lin.random.RandomData;
import com.lin.utils.RandomUtils;

/**
 * @author lkmc2
 * @date 2018/10/7
 * @description 手机号随机生成器（11位手机号）
 */
public class PhoneRandom implements RandomData<String> {

    // 手机号前缀数组
    private static final String[] phonePrefix = {
            "134", "135", "136", "137", "138", "139", "147", "150", "151",
            "152", "157", "158", "159", "182", "187", "188", "130", "131",
            "132", "155", "156", "185", "186", "130", "131", "132", "155",
            "156", "185", "186", "170", "199"
    };

    @Override
    public String next() {
        // 手机前缀
        String prefix = RandomUtils.selectOneInArray(phonePrefix);

        // 手机后缀
        int suffix = RandomUtils.nextIntRange(12345678, 99999998);

        return prefix + suffix;
    }

}
