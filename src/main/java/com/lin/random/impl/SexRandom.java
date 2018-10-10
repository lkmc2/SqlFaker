package com.lin.random.impl;

import com.lin.random.base.BaseRandomCreator;

/**
 * 随机性别（0 表示男，1 表示女）
 * @author lkmc2
 * @since 1.0.0
 */
public class SexRandom extends BaseRandomCreator<String> {

    @Override
    public String[] createOptionsArray() {
        return new String[]{"0", "1"};
    }

}
