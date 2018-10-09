package com.lin.asserts;

/**
 * @author lkmc2
 * @date 2018/10/7
 * @description 条件判断类
 */
public final class Asserts {

    /**
     * 条件必须为真，否则抛异常
     * @param expression 判断条件
     * @param message 异常信息
     * @param values 异常参数
     */
    public static void isTrue(boolean expression, String message, Object... values) {
        if (!expression) {
            throw new IllegalArgumentException(String.format(message, values));
        }
    }

}
