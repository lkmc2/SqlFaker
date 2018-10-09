package com.lin.random.impl;

import com.lin.random.RandomData;
import com.lin.worker.IdWorker;

/**
 * @author lkmc2
 * @date 2018/10/7
 * @description ID随机生成器（生成19位的数字型UUID字符串，如：1049120504188764160）
 */
public class IdRandom implements RandomData<Long> {

    // ID生成器
    private static final IdWorker ID_WORKER = new IdWorker(0, 0);

    @Override
    public Long next() {
        return ID_WORKER.nextId();
    }

}
