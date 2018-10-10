package com.lin.utils;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 享元模式工具类（全局运行环境中，相同的类共享同一个实例对象）
 * @author lkmc2
 * @since 1.0.0
 */
public class FlyweightUtils {

    /** 存储类和对应的实例对象 **/
    private static final ConcurrentMap<Class, Object> MAP = new ConcurrentHashMap<Class, Object>();

    /**
     * 获取指定类型的实例对象
     * @param type 类的Class类型
     * @param <T> 类的泛型
     * @return 指定类型的实例对象
     */
    @SuppressWarnings("unchecked")
    public static <T> T getInstance(Class<T> type) {
        // 从MAP中获取类型对应的实例对象
        Object obj = MAP.get(type);

        try {
            // 对象为空
            if (obj == null) {
                synchronized (MAP) {
                    // 使用该类的无参构造器生成实例对象
                    obj = type.newInstance();
                    // 将实例对象放入MAP中
                    MAP.put(type, obj);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(String.format("实例化[ %s ]对象失败", type.getName()));
        }
        return (T) obj;
    }

    /**
     * 移除指定类型的单例对象
     * @param type 类的Class类型
     * @param <T> 类的泛型
     */
    public static <T> void remove(Class<T> type) {
        MAP.remove(type);
    }

}