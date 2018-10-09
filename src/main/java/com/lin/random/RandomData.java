package com.lin.random;

/**
 * @author lkmc2
 * @date 2018/10/3
 * @description 随机数据接口
 * @since 1.0.0
 */
public interface RandomData<T> {

    /**
     * 生成一个随机值
     * @return 一个随机值
     */
    T next();

}
