package com.lin.random;

/**
 * 随机数据接口
 * @author lkmc2
 * @since 1.0.0
 */
public interface RandomData<T> {

    /**
     * 生成一个随机值
     * @return 一个随机值
     */
    T next();

}
